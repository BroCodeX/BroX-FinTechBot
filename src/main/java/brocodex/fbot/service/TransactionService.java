package brocodex.fbot.service;

import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.mapper.TransactionMapper;
import brocodex.fbot.repository.BudgetRepository;
import brocodex.fbot.repository.UserRepository;
import brocodex.fbot.repository.transactions.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
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

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO dto) {
        String type = dto.getType().toLowerCase(Locale.ROOT);
        Long telegramId = dto.getTelegramId();
        Double trnAmount = dto.getAmount();

        var maybeUser = userRepository.findByTelegramId(telegramId).orElseThrow(() ->
                new NoSuchElementException("There is no user with telegram id: " + telegramId));
        var maybeBudget = maybeUser.getBudget();
        dto.setBudget(maybeBudget.getId());

        var transaction = mapper.map(dto);

        Double currentBudgetAmount = maybeBudget.getAmount();

        if (type.equals("income")) {
            maybeBudget.setAmount(currentBudgetAmount + trnAmount);
            budgetRepository.save(maybeBudget);
            transactionRepository.save(transaction);
            return mapper.map(transaction);
        } else if (type.equals("expense")) {
            if (currentBudgetAmount < trnAmount) {
                String failMessage = "Not enough budget for the expense" + "\n" +
                        "Correct your budget first";
                throw new RuntimeException(failMessage);
            }
            maybeBudget.setAmount(currentBudgetAmount - trnAmount);
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

}
