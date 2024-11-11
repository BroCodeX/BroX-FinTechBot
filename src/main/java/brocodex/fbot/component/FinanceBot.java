package brocodex.fbot.component;

import brocodex.fbot.controller.bot.*;
import brocodex.fbot.service.handler.ResponseHandlerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.SilentSender;

@Component
public class FinanceBot extends AbilityBot {
    private final ResponseHandlerService responseHandlerService;
    private UserController userController;
    private BudgetController budgetController;
    private ReportController reportController;
    private TransactionsController transactionsController;

    private final long creatorID;

    public FinanceBot(@Value("${telegram.bot.token}") String botToken,
                      @Value("${telegram.bot.username}") String botUsername,
                      @Value("${telegram.bot.creatorId}") Long creatorId) {
        super(botToken, botUsername);
        this.creatorID = creatorId;
        this.responseHandlerService = new ResponseHandlerService(new SilentSender(this.sender), this.db());
    }

    @Override
    public long creatorId() {
        return creatorID;
    }

    @PostConstruct
    public void init() {
        System.out.println("Bot has been initialized!");
        start();
    }

    public Ability start() {
        return userController.start();
    }

    public Ability addTransaction() {
        return transactionsController.addTransaction();
    }


    public Ability viewTransactions() {
        return transactionsController.viewTransactions();
    }


    public Ability setBudget() {
        return budgetController.setBudget();
    }

    public Ability editBudget() {
        return budgetController.editBudget();
    }

    public Ability getReport() {
        return reportController.generateReport();
    }
}
