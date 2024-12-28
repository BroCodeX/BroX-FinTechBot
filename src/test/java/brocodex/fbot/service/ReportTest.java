package brocodex.fbot.service;

import brocodex.fbot.component.PdfReportGenerator;
import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.mapper.TransactionMapper;
import brocodex.fbot.model.Budget;
import brocodex.fbot.model.User;
import brocodex.fbot.model.transaction.Transaction;
import brocodex.fbot.model.transaction.TransactionCategory;
import brocodex.fbot.utils.ModelsGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportTest {
    @Mock
    private PdfReportGenerator pdfGenerator;

    private ModelsGenerator modelsGenerator = new ModelsGenerator();
    @Mock
    private TransactionMapper mapper;

    @InjectMocks
    private ReportService reportService;

    private User user;
    private Budget budget;
    private Transaction incomeTransaction;
    private Transaction expenseTransaction;
    private Transaction incomeTransaction2;
    private TransactionCategory category;
    private List<Transaction> transactions;

    @BeforeEach
    public void prepareData() {
        user = new User();
        user.setId(1L);
        user.setUsername("Yandex");
        user.setTelegramId(123L);

        budget = Instancio.of(modelsGenerator.makeBudget()).create(); // 10k
        budget.setId(1L);


        category = new TransactionCategory();
        category.setSlug("Yandex");

        incomeTransaction = Instancio.of(modelsGenerator.makeIncomeTransaction()).create();
        incomeTransaction.setId(1L);
        incomeTransaction2 = Instancio.of(modelsGenerator.makeIncomeTransaction()).create();
        incomeTransaction2.setId(2L);

        expenseTransaction = Instancio.of(modelsGenerator.makeExpenseTransaction()).create();
        expenseTransaction.setId(3L);

        transactions = List.of(incomeTransaction, expenseTransaction, incomeTransaction2);
    }

    @Test
    public void reportPdfTest() {
        List<TransactionDTO> dtoList = transactions.stream()
                .map(i -> mapper.map(i))
                .toList();

        when(mapper.map(transactions.get(0))).thenReturn(dtoList.get(0));
        when(mapper.map(transactions.get(1))).thenReturn(dtoList.get(1));
        when(mapper.map(transactions.get(2))).thenReturn(dtoList.get(2));

        File mockPdfFile = new File("report.pdf");
        when(pdfGenerator.generatePdfReport(dtoList)).thenReturn(mockPdfFile);

        File result = reportService.getReport(transactions);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("report.pdf");
    }
}
