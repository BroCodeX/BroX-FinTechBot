package brocodex.fbot.service;

import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.mapper.TransactionMapper;
import brocodex.fbot.repository.BudgetRepository;
import brocodex.fbot.repository.UserRepository;
import brocodex.fbot.repository.transactions.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private TransactionMapper mapper;

    public List<TransactionDTO> getAllTransactions(int limit) {
        return null;
    }

    public TransactionDTO createTransaction(TransactionDTO dto) {
        String type = dto.getType();
        Long telegramId = dto.getTelegramId();
        Double trnsAmount = dto.getAmount();
        var transaction = mapper.map(dto);

        var maybeUser = userRepository.findByTelegramId(telegramId).orElseThrow(NoSuchElementException::new);
        var maybeBudget = maybeUser.getBudget();
        Double currentBudgetAmount = maybeBudget.getAmount();

        if (type.equals("income")) {
            maybeBudget.setAmount(currentBudgetAmount + trnsAmount);
            budgetRepository.save(maybeBudget);
            transactionRepository.save(transaction);
            return mapper.map(transaction);
        } else if (type.equals("expense")) {
            if (currentBudgetAmount < trnsAmount) {
                throw new IllegalArgumentException("Not enough budget for the expense");
            }
            maybeBudget.setAmount(currentBudgetAmount - trnsAmount);
            budgetRepository.save(maybeBudget);
            transactionRepository.save(transaction);
            return mapper.map(transaction);
        } else {
            throw new RuntimeException("Unknown operation: " + type);
        }
    }

    public TransactionDTO showTransaction(Long id) {
        return null;
    }

    public void destroyTransaction(Long id) {

    }
}
