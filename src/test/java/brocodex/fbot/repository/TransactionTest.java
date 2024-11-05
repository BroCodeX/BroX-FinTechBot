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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionTest {
    private ModelsGenerator generator = new ModelsGenerator();

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionCategoryRepository categoryRepository;

    private TransactionSpec specification = new TransactionSpec();

    private Transaction incomeTransaction;
    private Transaction expenseTransaction;
    private TransactionCategory category;

    @BeforeEach
    public void prepareData() {
        incomeTransaction = Instancio.of(generator.makeIncomeTransaction()).create();
        TransactionCategory categorySalary = new TransactionCategory();
        categorySalary.setSlug("Salary");
        incomeTransaction.setCategory(categorySalary);

        expenseTransaction = Instancio.of(generator.makeExpenseTransaction()).create();
        TransactionCategory categoryElect = new TransactionCategory();
        categoryElect.setSlug("Electronic");
        expenseTransaction.setCategory(categoryElect);
    }

    @Test
    public void testWithStartDate() {
        var incomeTransaction2 = Instancio.of(generator.makeIncomeTransaction()).create();
        incomeTransaction2.setTransactionDate(LocalDate.of(1996, 8, 25));
        repository.save(incomeTransaction2);

        var incomeTransaction3 = Instancio.of(generator.makeIncomeTransaction()).create();
        incomeTransaction3.setTransactionDate(LocalDate.of(1981, 8, 25));
        repository.save(incomeTransaction3);

        var dto = new TransactionFilterDTO();
        dto.setStartDate(LocalDate.of(1991, 8, 25));

        var spec = specification.build(dto);

        List<Transaction> transactionList = repository.findAll(spec);

        List<Long> ids = new ArrayList<>();
        ids.add(incomeTransaction.getId());
        ids.add(incomeTransaction2.getId());

        assertThat(transactionList).isNotEmpty();
        assertThat(transactionList.size()).isEqualTo(2);
        assertThat(transactionList)
                .extracting(Transaction::getTransactionDate)
                .allMatch(date -> !date.isBefore(dto.getStartDate())); // Проверяем, что все даты равны или больше фильтра
    }

    @Test
    public void testWithEndDate() {

    }

    @Test
    public void testBetweenDates() {

    }
}
