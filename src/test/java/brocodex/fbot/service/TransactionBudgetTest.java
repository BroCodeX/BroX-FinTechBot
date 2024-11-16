package brocodex.fbot.service;

import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.model.Budget;
import brocodex.fbot.model.transaction.Transaction;
import brocodex.fbot.model.transaction.TransactionCategory;
import brocodex.fbot.repository.BudgetRepository;
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

    @BeforeEach
    public void prepareData() {

        incomeTransaction = Instancio.of(generator.makeIncomeTransaction()).create();
        incomeTransaction.setId(1L);
        expenseTransaction = Instancio.of(generator.makeExpenseTransaction()).create();
        expenseTransaction.setId(2L);
        budget = Instancio.of(generator.makeBudget()).create(); // 10k
        budget.setId(1L);

        dto = new TransactionDTO();
        dto.setAmount(5.000);
        dto.setDescription("Yandex transaction");
        dto.setCategoryName("Yandex");
        dto.setTransactionDate(NOW);
    }

    @Test
    public void testIncomeTransaction() {
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(ans -> {
            Transaction transaction = ans.getArgument(0);
            transaction.setId(1L);
            return transaction;
        });

        dto.setType("income");
        var result = service.createTransaction(dto);

        assertThat(result).isNotNull();
        assertThat(budget.getAmount()).isEqualTo(15.000);

        verify(budgetRepository).findById(1L);
        verify(budgetRepository).save(budget);
        verify(transactionRepository.save(any(Transaction.class)));
    }

    @Test
    public void testExpenseTransaction() {
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(ans -> {
           Transaction transaction = ans.getArgument(0);
           transaction.setId(2L);
           return transaction;
        });

        dto.setType("expense");
    }
}
