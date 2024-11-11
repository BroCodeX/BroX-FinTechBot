package brocodex.fbot.controller.bot;

import brocodex.fbot.handler.ResponseHandler;
import org.telegram.abilitybots.api.objects.Ability;

public class BudgetController {
    private final ResponseHandler responseHandler;

    public BudgetController(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public Ability setBudget() {
        return Ability.builder().build();
    }

    public Ability editBudget() {
        return Ability.builder().build();
    }

    public Ability generateReport() {
        return Ability.builder().build();
    }
}
