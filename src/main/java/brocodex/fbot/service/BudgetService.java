package brocodex.fbot.service;

import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.mapper.BudgetMapper;
import brocodex.fbot.repository.BudgetRepository;
import brocodex.fbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetMapper mapper;

    @Autowired
    private ChatStateService chatStateService;

    public SendMessage setBudget(Long chatId, String amount, Long userId) {
        try {
            BudgetDTO dto = new BudgetDTO();
            dto.setAmount(Double.parseDouble(amount));
            dto.setTelegramId(userId);

            var maybeUser = userRepository.findByTelegramId(dto.getTelegramId())
                    .orElse(null);
            if(maybeUser == null) {
                return SendMessage
                        .builder()
                        .chatId(chatId)
                        .text("User with such id not found: " + userId + "\nYou need to register first")
                        .build();
            }

            BudgetDTO budget;

            if(maybeUser.getBudget() != null) {
                budget = updateBudget(chatId, dto);
            } else {
                budget = createBudget(dto);
            }

            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Your budget is set to: " + budget.getAmount() + "\n" + "\n" +
                            CommandMessages.HELP_MESSAGE.getDescription())
                    .build();
            sendMessage.setChatId(chatId);
            chatStateService.setChatState(chatId, null);

            return sendMessage;
        } catch (NumberFormatException ex) {
            return SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Your message is incorrect. Please enter a valid data for your budget")
                    .build();
        }
    }

    public SendMessage showBudget(Long userID, Long chatID) {
        var maybeBudget = userRepository.findByTelegramId(userID).orElse(null);
        if(maybeBudget == null) {
            return SendMessage
                    .builder()
                    .chatId(chatID)
                    .text("Budget with this user id: " + userID + " not found")
                    .build();
        }
        return SendMessage
                .builder()
                .chatId(chatID)
                .text("Your current budget: \n" +
                        "amount: " + maybeBudget.getBudget().getAmount() + "\n" +
                        "created at: " + maybeBudget.getBudget().getCreatedAt())
                .build();
    }

    public BudgetDTO createBudget(BudgetDTO dto) {
        var maybeUser = userRepository.findByTelegramId(dto.getTelegramId()).get();

        var budget = mapper.map(dto);
        repository.save(budget);

        maybeUser.setBudget(budget);       // Связываем бюджет с пользователем
        userRepository.save(maybeUser);   // Сохраняем пользователя

        return mapper.map(budget);
        }

//    public BudgetDTO updateBudget(BudgetDTO dto) {
//        var maybeUser = userRepository.findByTelegramId(dto.getTelegramId()).get();
//
//        var currentBudget = maybeUser.getBudget();
//        currentBudget.setAmount(dto.getAmount());
//        repository.save(currentBudget);
//        return mapper.map(currentBudget);
//    }

    public BudgetDTO updateBudget(Long chatId, BudgetDTO dto) {
        var maybeUser = userRepository.findByTelegramId(dto.getTelegramId()).get();

        var currentBudget = maybeUser.getBudget();
        maybeUser.setBudget(null);
        repository.delete(currentBudget);
        userRepository.save(maybeUser);

        return createBudget(dto);
    }
}
