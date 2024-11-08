package brocodex.fbot.controller.bot;

import brocodex.fbot.handler.ResponseHandler;
import org.telegram.abilitybots.api.objects.Ability;

public class BotCommandsController {
    private final ResponseHandler responseHandler;

    public BotCommandsController(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public Ability start() {
        return Ability.builder().build();
    }

    public Ability addTransaction() {
        return Ability.builder().build();
    }


    public Ability viewTransactions() {
        return Ability.builder().build();
    }


    public Ability setBudget() {
        return Ability.builder().build();
    }

    public Ability editBudget() {
        return Ability.builder().build();
    }

    public Ability getReport() {
        return Ability.builder().build();
    }
}
