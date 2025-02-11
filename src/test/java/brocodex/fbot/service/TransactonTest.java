package brocodex.fbot.service;

import brocodex.fbot.dto.transaction.TransactionDTO;
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
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TransactonTest {
    @Autowired
    private ModelsGenerator generator;

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

    private TransactionCategory category;
    private Budget budget;

    private TransactionDTO dto;

    private User user;

    @BeforeEach
    public void prepareData() {
        transactionRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();


        budget = Instancio.of(generator.makeBudget()).create(); // 10k

        dto = new TransactionDTO();
        dto.setAmount(5.000);
        dto.setDescription("Yandex transaction");
        dto.setCategory("Yandex");
        dto.setTransactionDate(NOW);

        user = Instancio.of(generator.makeUser()).create();
        user.setBudget(budget);
        userRepository.save(user);

        category = new TransactionCategory();
        category.setSlug("Yandex");
        categoryRepository.save(category);
    }

    @Test
    public void testIncomeTransaction() {
        dto.setType("income");
        dto.setTelegramId(user.getTelegramId());

        Long budgetId = user.getBudget().getId();
        dto.setBudget(budgetId);

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

        Long budgetId = user.getBudget().getId();
        dto.setBudget(budgetId);

        var currentTransaction = service.createTransaction(dto);
        Long transactionId = currentTransaction.getId();

        var maybeBudget = budgetRepository.findById(budgetId).orElse(null);
        assertThat(maybeBudget).isNotNull();
        assertThat(maybeBudget.getAmount()).isEqualTo(5.000);

        var maybeTransaction = transactionRepository.findById(transactionId).orElse(null);
        assertThat(maybeTransaction).isNotNull();
        assertThat(maybeTransaction.getType()).isEqualTo("expense");
    }

    @Test
    public void testExpenseFailedTransaction() {
        dto.setType("expense");
        dto.setAmount(20.000);
        dto.setTelegramId(user.getTelegramId());

        Long budgetId = user.getBudget().getId();
        dto.setBudget(budgetId);

        var exception = assertThrows(RuntimeException.class,
                () -> service.createTransaction(dto));

        assertThat(exception.getMessage()).isEqualTo("Not enough budget for the expense" + "\n" +
                "Correct your budget first");

        var maybeBudget = budgetRepository.findById(budgetId).orElse(null);
        assertThat(maybeBudget).isNotNull();
        assertThat(maybeBudget.getAmount()).isEqualTo(10.000);
    }
}
