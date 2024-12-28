package brocodex.fbot.service;

import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.mapper.BudgetMapper;
import brocodex.fbot.repository.BudgetRepository;
import brocodex.fbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public SendMessage setBudget(Long chatId, String amount, Long userId) {
        try {
            BudgetDTO dto = new BudgetDTO();
            var budgetAmount = Double.parseDouble(amount);

            if (budgetAmount <= 0) {
                return SendMessage
                        .builder()
                        .chatId(chatId)
                        .text("Your amount is below zero: " + amount)
                        .build();
            }

            dto.setAmount(budgetAmount);
            dto.setTelegramId(userId);

            SendMessage sendMessage = createOrUpdate(dto, chatId);
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

    public SendMessage createOrUpdate(BudgetDTO dto, Long chatId) {
        var user = userRepository.findByTelegramId(dto.getTelegramId())
                .orElse(null);
        if(user == null) {
            return SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("User with such id not found: " + dto.getTelegramId() + "\nYou need to register first")
                    .build();
        }

        BudgetDTO budget;

        if(user.getBudget() != null) {
            budget = updateBudget(dto);
        } else {
            budget = createBudget(dto);
        }

        return SendMessage
                .builder()
                .chatId(chatId)
                .text("Your budget is set to: " + budget.getAmount() + "\n" + "\n" +
                        CommandMessages.HELP_MESSAGE.getDescription())
                .build();
    }

    @Transactional
    public SendMessage showBudget(Long userID, Long chatID) {
        var maybeUser = userRepository.findByTelegramId(userID).orElse(null);
        if(maybeUser == null) {
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
                        "amount: " + maybeUser.getBudget().getAmount() + "\n" +
                        "created at: " + maybeUser.getBudget().getCreatedAt())
                .build();
    }

    public BudgetDTO createBudget(BudgetDTO dto) {
        var maybeUser = userRepository.findByTelegramId(dto.getTelegramId()).get();

        var budget = mapper.map(dto);

        maybeUser.setBudget(budget);       // Связываем бюджет с пользователем
        userRepository.save(maybeUser);   // Сохраняем пользователя

        return mapper.map(budget);
    }

    @Transactional
    public BudgetDTO updateBudget(BudgetDTO dto) {
        var maybeUser = userRepository.findByTelegramId(dto.getTelegramId()).get();

        var currentBudget = maybeUser.getBudget();
        currentBudget.setAmount(dto.getAmount());

        maybeUser.setBudget(currentBudget);
        userRepository.save(maybeUser);

        return mapper.map(currentBudget);
    }
}
