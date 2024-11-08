package brocodex.fbot.component;

import brocodex.fbot.controller.bot.BotCommandsController;
import brocodex.fbot.handler.ResponseHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.SilentSender;

@Component
public class FinanceBot extends AbilityBot {
    private final ResponseHandler responseHandler;
    private BotCommandsController commandsController;

    private final long creatorID;

    public FinanceBot(@Value("${telegram.bot.token}") String botToken,
                      @Value("${telegram.bot.username}") String botUsername,
                      @Value("${telegram.bot.creatorId}") Long creatorId) {
        super(botToken, botUsername);
        this.creatorID = creatorId;
        this.responseHandler = new ResponseHandler(new SilentSender(this.sender), this.db());
    }

    @Override
    public long creatorId() {
        return creatorID;
    }

    @PostConstruct
    public void init() {
        System.out.println("Starting bot...");
        System.out.println("Starting has started!");
    }

    public Ability start() {
        return commandsController.start();
    }

    public Ability add_transaction() {
        return commandsController.addTransaction();
    }


    public Ability view_transactions() {
        return commandsController.viewTransactions();
    }


    public Ability set_budget() {
        return commandsController.setBudget();
    }

    public Ability edit_budget() {
        return commandsController.editBudget();
    }

    public Ability get_report() {
        return commandsController.getReport();
    }
}
