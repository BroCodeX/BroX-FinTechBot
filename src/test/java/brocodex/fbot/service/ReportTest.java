package brocodex.fbot.service;

import brocodex.fbot.component.PdfReportGenerator;
import brocodex.fbot.mapper.TransactionMapper;
import brocodex.fbot.model.Budget;
import brocodex.fbot.model.User;
import brocodex.fbot.model.transaction.Transaction;
import brocodex.fbot.model.transaction.TransactionCategory;
import brocodex.fbot.utils.ModelsGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;

public class ReportTest {
    @Autowired
    private PdfReportGenerator pdfGenerator;
    @Autowired
    private ModelsGenerator modelsGenerator;
    @Autowired
    private TransactionMapper mapper;
    @Autowired
    private ReportService reportService;

    private User user;
    private Budget budget;
    private Transaction incomeTransaction;
    private Transaction expenseTransaction;
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

        expenseTransaction = Instancio.of(modelsGenerator.makeExpenseTransaction()).create();
        expenseTransaction.setId(2L);

        transactions = List.of(incomeTransaction, expenseTransaction);
    }

    @Test
    public void reportPdfTest() {
        var dtos = when(transactions.stream()
                .map(i -> mapper.map(i))
                .toList())
                .thenAnswer(invocation -> transactions.stream()
                        .map(i -> mapper.map(i))
                        .toList());

        var file = pdfGenerator.generatePdfReport(dtos);
    }
}
