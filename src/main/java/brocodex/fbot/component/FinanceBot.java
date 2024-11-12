package brocodex.fbot.component;

import brocodex.fbot.controller.bot.*;
import brocodex.fbot.service.handler.ResponseHandlerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.*;
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
        return Ability.builder()
                .name("start")
                .info("Starts the bot")
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    Long chatId = ctx.chatId();
                    userController.welcomeUser(chatId);
                })
                .build();
    }

    public Ability addTransaction() {
        return Ability.builder()
                .name("add_transaction")
                .info("Add an income / expense transaction")
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    Long chatId = ctx.chatId();
                    transactionsController.replyToAddTransaction(chatId);
                })
                .build();
    }


    public Ability viewTransactionsReport() {
        return Ability.builder()
                .name("view_transactions")
                .info("Watch your transactions, optionally set period")
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    Long chatId = ctx.chatId();
                    //
                })
                .build();
    }

    public Ability editBudget() {
        return Ability.builder()
                .name("edit_budget")
                .info("Set a new budget amount")
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    Long chatId = ctx.chatId();
                    //budgetController.editBudget();
                })
                .build();
    }

    public Ability getBudgetReport() {
        return Ability.builder()
                .name("view_budget")
                .info("Watch your budget")
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    Long chatId = ctx.chatId();
                    //
                })
                .build();
    }

    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (abilityBot, upd) ->
                responseHandlerService.replyToButtons(getChatId(upd), upd.getMessage());
        return Reply.of(action, Flag.TEXT,upd -> responseHandlerService.userIsActive(getChatId(upd)));
    }
}
