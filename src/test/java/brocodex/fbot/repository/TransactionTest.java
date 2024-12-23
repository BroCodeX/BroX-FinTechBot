package brocodex.fbot.repository;

import brocodex.fbot.dto.transaction.report.TransactionFilterDTO;
import brocodex.fbot.model.transaction.Transaction;
import brocodex.fbot.model.transaction.TransactionCategory;
import brocodex.fbot.repository.transactions.TransactionCategoryRepository;
import brocodex.fbot.repository.transactions.TransactionRepository;
import brocodex.fbot.specification.TransactionSpec;
import brocodex.fbot.utils.ModelsGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionTest {
    private ModelsGenerator generator = new ModelsGenerator();

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionCategoryRepository categoryRepository;

    private static final LocalDateTime DATE_1996 =
            LocalDateTime.of(1996, 8, 25, 10, 10);
    private static final LocalDateTime DATE_1981 =
            LocalDateTime.of(1981, 8, 25, 10, 10);
    private static final LocalDateTime DATE_1991 =
            LocalDateTime.of(1991, 8, 25, 10, 10);
    private static final LocalDateTime NOW = LocalDateTime.now();

    private TransactionSpec specification = new TransactionSpec();

    private Transaction incomeTransaction;
    private Transaction expenseTransaction;
    private TransactionCategory category;

    @BeforeEach
    public void prepareData() {
        repository.deleteAll();
        incomeTransaction = Instancio.of(generator.makeIncomeTransaction()).create();
        expenseTransaction = Instancio.of(generator.makeExpenseTransaction()).create();
    }

    @Test
    public void testWithStartDateSpec() {
        repository.save(incomeTransaction);
        incomeTransaction.setTransactionDate(DATE_1991);
        repository.save(incomeTransaction);

        var incomeTransaction2 = Instancio.of(generator.makeIncomeTransaction()).create();
        repository.save(incomeTransaction2);
        incomeTransaction2.setTransactionDate(DATE_1996);
        repository.save(incomeTransaction2);

        var incomeTransaction3 = Instancio.of(generator.makeIncomeTransaction()).create();
        repository.save(incomeTransaction3);
        incomeTransaction3.setTransactionDate(DATE_1981);
        repository.save(incomeTransaction3);

        var dto = new TransactionFilterDTO();
        dto.setStartDate(DATE_1991);

        var spec = specification.build(dto);

        List<Transaction> transactionList = repository.findAll(spec);

        assertThat(transactionList).isNotEmpty();
        assertThat(transactionList.size()).isEqualTo(2);
        assertThat(transactionList)
                .extracting(Transaction::getTransactionDate)
                .allMatch(date -> !date.isBefore((dto.getStartDate())));
    }

    @Test
    public void testWithEndDateSpec() {
        repository.save(incomeTransaction);
        incomeTransaction.setTransactionDate(DATE_1991);
        repository.save(incomeTransaction);

        var incomeTransaction2 = Instancio.of(generator.makeIncomeTransaction()).create();
        repository.save(incomeTransaction2);
        incomeTransaction2.setTransactionDate(DATE_1996);
        repository.save(incomeTransaction2);

        var incomeTransaction3 = Instancio.of(generator.makeIncomeTransaction()).create();
        repository.save(incomeTransaction3);
        incomeTransaction3.setTransactionDate(DATE_1981);
        repository.save(incomeTransaction3);

        var dto = new TransactionFilterDTO();
        dto.setEndDate(DATE_1996);

        var spec = specification.build(dto);

        List<Transaction> transactionList = repository.findAll(spec);

        assertThat(transactionList).isNotEmpty();
        assertThat(transactionList.size()).isEqualTo(3);
        assertThat(transactionList)
                .extracting(Transaction::getTransactionDate)
                .allMatch(date -> !date.isAfter((dto.getEndDate())));
    }

    @Test
    public void testBetweenDatesSpec() {
        repository.save(incomeTransaction);
        incomeTransaction.setTransactionDate(DATE_1991);
        repository.save(incomeTransaction);

        var incomeTransaction2 = Instancio.of(generator.makeIncomeTransaction()).create();
        repository.save(incomeTransaction2);
        incomeTransaction2.setTransactionDate(DATE_1996);
        repository.save(incomeTransaction2);

        var incomeTransaction3 = Instancio.of(generator.makeIncomeTransaction()).create();
        repository.save(incomeTransaction3);
        incomeTransaction3.setTransactionDate(DATE_1981);
        repository.save(incomeTransaction3);

        var incomeTransactionNow = Instancio.of(generator.makeIncomeTransaction()).create();
        repository.save(incomeTransactionNow);

        var dto = new TransactionFilterDTO();
        dto.setStartDate(DATE_1991);
        dto.setEndDate(DATE_1996);

        var spec = specification.build(dto);

        List<Transaction> transactionList = repository.findAll(spec);

        assertThat(transactionList).isNotEmpty();
        assertThat(transactionList.size()).isEqualTo(2);
        assertThat(transactionList)
                .extracting(Transaction::getTransactionDate)
                .allMatch(date -> !date.isBefore(dto.getStartDate()) && !date.isAfter(dto.getEndDate()));
    }

    @Test
    public void categoryTestSpec() {
        category = new TransactionCategory();
        category.setSlug("Food");
        categoryRepository.save(category);

        incomeTransaction.setCategory(category);
        expenseTransaction.setCategory(category);

        repository.save(incomeTransaction);
        repository.save(expenseTransaction);

        var dto = new TransactionFilterDTO();
        dto.setCategory("food");

        var spec = specification.build(dto);

        List<Transaction> transactionList = repository.findAll(spec);

        assertThat(transactionList.size()).isEqualTo(2);
        assertThat(transactionList)
                .extracting(Transaction::getCategory)
                .allMatch(cat -> cat.getSlug().equals("Food"));
    }

    @Test
    public void typeTestSpec() {
        var incomeTrans2 = Instancio.of(generator.makeIncomeTransaction()).create();
        var incomeTrans3 = Instancio.of(generator.makeIncomeTransaction()).create();
        var expenseTrans2 = Instancio.of(generator.makeExpenseTransaction()).create();

        repository.save(incomeTransaction);
        repository.save(incomeTrans2);
        repository.save(incomeTrans3);
        repository.save(expenseTransaction);
        repository.save(expenseTrans2);

        var dto = new TransactionFilterDTO();
        dto.setOperationType("all");

        var spec = specification.build(dto);

        List<Transaction> transactionList = repository.findAll(spec);

        assertThat(transactionList.size()).isEqualTo(5);
    }
}
