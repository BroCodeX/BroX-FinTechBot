package brocodex.fbot.service;

import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.dto.user.UserDTO;
import brocodex.fbot.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository repository;

    public BudgetDTO showBudget(Long id) {
        return null;
    }

    public BudgetDTO createBudget(BudgetDTO dto) {
        return null;
    }

    public BudgetDTO updateBudget(Long id, BudgetDTO dto) {
        return null;
    }

    public void destroyBudget(Long id) {

    }
}
