package brocodex.fbot.controller.bot;

import brocodex.fbot.handler.ResponseHandler;
import org.telegram.abilitybots.api.objects.Ability;

public class TransactionsController {
    private final ResponseHandler responseHandler;

    public TransactionsController(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public Ability addTransaction() {
        return Ability.builder().build();
    }

    public Ability viewTransactions() {
        return Ability.builder().build();
    }

}
