package brocodex.fbot.service;

import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.dto.user.UserDTO;
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
        var budget = mapper.map(dto);
        repository.save(budget);

        var maybeUser = userRepository.findByTelegramId(dto.getTelegramId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        maybeUser.setBudget(budget);       // Связываем бюджет с пользователем
        userRepository.save(maybeUser);   // Сохраняем пользователя

        return mapper.map(budget);
    }

    public BudgetDTO updateBudget(Long id, BudgetDTO dto) {
        return null;
    }

    public void destroyBudget(Long id) {

    }
}
