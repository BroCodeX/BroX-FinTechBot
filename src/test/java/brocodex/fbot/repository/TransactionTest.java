package brocodex.fbot.repository;

import brocodex.fbot.model.transaction.Transaction;
import brocodex.fbot.model.transaction.TransactionCategory;
import brocodex.fbot.repository.transactions.TransactionCategoryRepository;
import brocodex.fbot.repository.transactions.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TransactionTest {
    private Transaction transaction;
    private TransactionCategory category;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionCategoryRepository categoryRepository;

    @BeforeEach
    public void prepareData() {

    }
}
