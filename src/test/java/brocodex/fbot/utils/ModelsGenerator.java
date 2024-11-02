package brocodex.fbot.utils;

import brocodex.fbot.model.Budget;
import brocodex.fbot.model.Notification;
import brocodex.fbot.model.User;
import brocodex.fbot.model.transaction.Transaction;
import brocodex.fbot.repository.transactions.TransactionCategoryRepository;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelsGenerator {
    @Autowired
    private TransactionCategoryRepository categoryRepository;

    @Autowired
    private Faker faker;

    @PostConstruct
    public void initFakeModels() {
        var user = makeUser();
        var expenseTransaction = makeExpenseTransaction();
        var incomeTransaction = makeIncomeTransaction();
        var budget = makeBudget();
    }

    public Model<User> makeUser() {
        return Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getUsername), () -> faker.name().firstName())
                .supply(Select.field(User::getTelegramId), () -> faker.number().randomNumber())
                .toModel();
    }

    public Model<Transaction> makeExpenseTransaction() {
        return Instancio.of(Transaction.class)
                .ignore(Select.field(Transaction::getId))
                .supply(Select.field(Transaction::getAmount), () -> 1500.00)
                .supply(Select.field(Transaction::getDescription), () -> "Buy a new iPhone")
                .supply(Select.field(Transaction::getType), () -> "EXPENSE")
                .supply(Select.field(Transaction::getCategory), () -> categoryRepository.findBySlug("Electronic"))
                .toModel();
    }

    public Model<Transaction> makeIncomeTransaction() {
        return Instancio.of(Transaction.class)
                .ignore(Select.field(Transaction::getId))
                .supply(Select.field(Transaction::getAmount), () -> 5000.00)
                .supply(Select.field(Transaction::getDescription), () -> "Had a salary")
                .supply(Select.field(Transaction::getType), () -> "INCOME")
                .supply(Select.field(Transaction::getCategory), () -> categoryRepository.findBySlug("Salary"))
                .toModel();
    }

    public Model<Budget> makeBudget() {
        return Instancio.of(Budget.class)
                .ignore(Select.field(Budget::getId))
                .ignore(Select.field(Budget::getSpentAmount))
                .supply(Select.field(Budget::getAmount), () -> 10.000)
                .toModel();
    }
}
