package brocodex.fbot.component;

import brocodex.fbot.controller.bot.*;
import brocodex.fbot.service.handler.ResponseHandlerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiConsumer;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class FinanceBot extends AbilityBot {
    private final ResponseHandlerService responseHandlerService;
    private final UserController userController;
    private final BudgetController budgetController;
    private final ReportController reportController;
    private final TransactionsController transactionsController;

    private final long creatorID;

    public FinanceBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            @Value("${telegram.bot.creatorId}") Long creatorId,
            ResponseHandlerService responseHandlerService,
            UserController userController,
            BudgetController budgetController,
            ReportController reportController,
            TransactionsController transactionsController) {
        super(botToken, botUsername);
        this.creatorID = creatorId;
        this.responseHandlerService = responseHandlerService;
        this.userController = userController;
        this.budgetController = budgetController;
        this.reportController = reportController;
        this.transactionsController = transactionsController;
    }

    @Override
    public long creatorId() {
        return creatorID;
    }

    @PostConstruct
    public void init() {
        System.out.println("Bot has been initialized!");
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

    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (abilityBot, upd) -> responseHandlerService.replyToButtons(getChatId(upd), upd.getMessage());
        return Reply.of(action, Flag.TEXT,upd -> responseHandlerService.userIsActive(getChatId(upd)));
    }
}
