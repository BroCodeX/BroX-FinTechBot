package brocodex.fbot.service;

import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.dto.transaction.report.TransactionFilterDTO;
import brocodex.fbot.model.Budget;
import brocodex.fbot.model.User;
import brocodex.fbot.model.transaction.Transaction;
import brocodex.fbot.model.transaction.TransactionCategory;
import brocodex.fbot.repository.BudgetRepository;
import brocodex.fbot.repository.UserRepository;
import brocodex.fbot.repository.transactions.TransactionCategoryRepository;
import brocodex.fbot.repository.transactions.TransactionRepository;
import brocodex.fbot.specification.TransactionSpec;
import brocodex.fbot.utils.ModelsGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TransactonTest {
    private ModelsGenerator generator = new ModelsGenerator();

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionCategoryRepository categoryRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private TransactionService service;

    @Autowired
    private UserRepository userRepository;

    private static final LocalDateTime DATE_1991 =
            LocalDateTime.of(1991, 8, 25, 10, 10);
    private static final LocalDateTime NOW = LocalDateTime.now();

    private TransactionSpec specification = new TransactionSpec();

    private Transaction incomeTransaction;
    private Transaction expenseTransaction;
    private TransactionCategory category;
    private Budget budget;

    private TransactionDTO dto;

    private User user;

    @BeforeEach
    public void prepareData() {
        budgetRepository.deleteAll();
        transactionRepository.deleteAll();
        userRepository.deleteAll();

        incomeTransaction = Instancio.of(generator.makeIncomeTransaction()).create();
        expenseTransaction = Instancio.of(generator.makeExpenseTransaction()).create();

        budget = Instancio.of(generator.makeBudget()).create(); // 10k

        dto = new TransactionDTO();
        dto.setAmount(5.000);
        dto.setDescription("Yandex transaction");
        dto.setCategoryName("Yandex");
        dto.setTransactionDate(NOW);

        user = Instancio.of(generator.makeUser()).create();
        budget.setUser(user);
        user.setBudget(budget);
    }

    @Test
    public void testIncomeTransaction() {
        dto.setType("income");
        dto.setTelegramId(user.getTelegramId());
        userRepository.save(user);

        var currentBudget = budgetRepository.save(budget);
        Long budgetId = currentBudget.getId();

        var currentTransaction = service.createTransaction(dto);
        Long transactionId = currentTransaction.getId();

        var maybeBudget = budgetRepository.findById(budgetId).orElse(null);
        assertThat(maybeBudget).isNotNull();
        assertThat(maybeBudget.getAmount()).isEqualTo(15.000);

        var maybeTransaction = transactionRepository.findById(transactionId).orElse(null);
        assertThat(maybeTransaction).isNotNull();
        assertThat(maybeTransaction.getType()).isEqualTo("income");
    }

    @Test
    public void testExpenseTransaction() {
        dto.setType("expense");
        dto.setTelegramId(user.getTelegramId());

        userRepository.save(user);

        var currentBudget = budgetRepository.save(budget);
        Long budgetId = currentBudget.getId();

        var currentTransaction = service.createTransaction(dto);
        Long transactionId = currentTransaction.getId();

        var maybeBudget = budgetRepository.findById(budgetId).orElse(null);
        assertThat(maybeBudget).isNotNull();
        assertThat(maybeBudget.getAmount()).isEqualTo(5.000);

        var maybeTransaction = transactionRepository.findById(transactionId).orElse(null);
        assertThat(maybeTransaction).isNotNull();
        assertThat(maybeTransaction.getType()).isEqualTo("income");
    }

    @Test
    public void testExpenseFailedTransaction() {
        dto.setType("expense");
        dto.setAmount(20.000);
        dto.setTelegramId(user.getTelegramId());
        userRepository.save(user);

        var currentBudget = budgetRepository.save(budget);
        Long budgetId = currentBudget.getId();

        var exception = assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction(dto));

        assertThat(exception.getMessage()).isEqualTo("Not enough budget for the expense");

        var maybeBudget = budgetRepository.findById(budgetId).orElse(null);
        assertThat(maybeBudget).isNotNull();
        assertThat(maybeBudget.getAmount()).isEqualTo(5.000);

//        var maybeTransaction = transactionRepository.findById(transactionId).orElse(null);
//        assertThat(maybeTransaction).isNotNull();
//        assertThat(maybeTransaction.getType()).isEqualTo("income");
    }
}
