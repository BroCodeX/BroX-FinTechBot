package brocodex.fbot.service;

import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.dto.user.UserDTO;
import brocodex.fbot.mapper.BudgetMapper;
import brocodex.fbot.repository.BudgetRepository;
import brocodex.fbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetMapper mapper;

    public BudgetDTO showBudget(Long id) {
        return null;
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
