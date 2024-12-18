package brocodex.fbot.service;

import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.mapper.TransactionMapper;
import brocodex.fbot.model.Budget;
import brocodex.fbot.model.User;
import brocodex.fbot.model.transaction.Transaction;
import brocodex.fbot.model.transaction.TransactionCategory;
import brocodex.fbot.repository.BudgetRepository;
import brocodex.fbot.repository.UserRepository;
import brocodex.fbot.repository.transactions.TransactionRepository;
import brocodex.fbot.specification.TransactionSpec;
import brocodex.fbot.utils.ModelsGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionBudgetTest {
    private ModelsGenerator generator = new ModelsGenerator();

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionMapper mapper;

    @InjectMocks
    private TransactionService service;

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
        user = new User();
        user.setId(1L);
        user.setUsername("Yandex");
        user.setTelegramId(123L);

        budget = Instancio.of(generator.makeBudget()).create(); // 10k
        budget.setId(1L);

        dto = new TransactionDTO();
        dto.setAmount(5.000);
        dto.setDescription("Yandex transaction");
        dto.setCategoryName("Yandex");
        dto.setTransactionDate(NOW);
        dto.setTelegramId(user.getTelegramId());
    }

    @Test
    public void testIncomeTransaction() {
        dto.setType("income");

        when(mapper.map(dto)).thenAnswer(invocation -> {
            Transaction transaction = new Transaction();
            transaction.setId(1L);
            transaction.setAmount(dto.getAmount());
            transaction.setType(dto.getType());
            return transaction;
        });

        when(userRepository.findByTelegramId(user.getTelegramId())).thenReturn(Optional.of(user));
        user.setBudget(budget);
//        budget.setUser(user);

        when(mapper.map(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setId(transaction.getId());
            transactionDTO.setAmount(transaction.getAmount());
            transactionDTO.setType(transaction.getType());
            return transactionDTO;
        });

        var result = service.createTransaction(dto);

        assertThat(result).isNotNull();
        assertThat(budget.getAmount()).isEqualTo(15.000);

        verify(budgetRepository).save(budget);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    public void testExpenseTransaction() {
        dto.setType("expense");

        when(userRepository.findByTelegramId(user.getTelegramId())).thenReturn(Optional.of(user));
        user.setBudget(budget);
//        budget.setUser(user);

        when(mapper.map(dto)).thenAnswer(invocation -> {
            Transaction transaction = new Transaction();
            transaction.setId(1L);
            transaction.setAmount(dto.getAmount());
            transaction.setType(dto.getType());
            return transaction;
        });

        when(mapper.map(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setId(transaction.getId());
            transactionDTO.setAmount(transaction.getAmount());
            transactionDTO.setType(transaction.getType());
            return transactionDTO;
        });

        var result = service.createTransaction(dto);

        assertThat(result).isNotNull();
        assertThat(budget.getAmount()).isEqualTo(5.000);

        verify(budgetRepository).save(budget);
        verify(transactionRepository).save(any(Transaction.class));
    }
}
