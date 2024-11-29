package brocodex.fbot.component;

import brocodex.fbot.controller.bot.*;
import brocodex.fbot.service.handler.CallbackHandlerService;
import brocodex.fbot.service.handler.ResponseHandlerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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
    private final CallbackHandlerService callbackHandlerService;

    private final long creatorID;

    public FinanceBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.name}") String botName,
            @Value("${telegram.bot.creatorId}") Long creatorId) {
        super(botToken, botName);
        this.creatorID = creatorId;
        this.responseHandlerService = new ResponseHandlerService(this.silent(), this.db());
        this.userController = new UserController(responseHandlerService);
        this.budgetController = new BudgetController(responseHandlerService);
        this.reportController = new ReportController(responseHandlerService);
        this.callbackHandlerService = new CallbackHandlerService();
        this.transactionsController = new TransactionsController(responseHandlerService, callbackHandlerService);

    }

//@Component
//public class FinanceBot extends AbilityBot {
//    @Autowired @Lazy
//    private ResponseHandlerService responseHandlerService;
//    @Autowired
//    private UserController userController;
//    @Autowired
//    private BudgetController budgetController;
//    @Autowired
//    private ReportController reportController;
//    @Autowired
//    private TransactionsController transactionsController;
//    @Autowired
//    private CallbackHandlerService callbackHandlerService;
//
//    private final long creatorID;
//
//    @Autowired
//    public FinanceBot(
//            @Value("${telegram.bot.token}") String botToken,
//            @Value("${telegram.bot.name}") String botName,
//            @Value("${telegram.bot.creatorId}") Long creatorId) {
//        super(botToken, botName);
//        this.creatorID = creatorId;
//    }

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
                    Long telegramId = ctx.user().getId();
                    transactionsController.replyToAddTransaction(chatId, telegramId);
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
                    Long telegramId = ctx.user().getId();
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
                    Long telegramId = ctx.user().getId();
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
                    Long telegramId = ctx.user().getId();
                    Long chatId = ctx.chatId();
                    //
                })
                .build();
    }

    public Ability help() {
        return Ability.builder()
                .name("help")
                .info("Displays a list of all available commands")
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    Long chatId = ctx.chatId();
                    StringBuilder helpMessage = new StringBuilder("Available commands:\n\n");

                    helpMessage.append("/add_transaction - Add a transaction");
                    helpMessage.append("/view_transactions - View your transactions with filters");
                    helpMessage.append("/edit_budget - Set or update your budget");
                    helpMessage.append("/view_budget - Watch your current budget");

                    ctx.bot().silent().send(helpMessage.toString(), chatId);
                })
                .build();
    }

    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (abilityBot, upd) ->
                responseHandlerService.replyToButtons(getChatId(upd), upd.getMessage());
        return Reply.of(action, Flag.TEXT,upd -> responseHandlerService.userIsActive(getChatId(upd)));
    }

    public void onUpdateReceived(Update update) {
        if(update.hasCallbackQuery()) {
            callbackHandlerService.handleCallback(update.getCallbackQuery());
        }
    }
}
