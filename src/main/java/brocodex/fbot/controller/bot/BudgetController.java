package brocodex.fbot.controller.bot;

import brocodex.fbot.service.handler.ResponseHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.abilitybots.api.objects.Ability;

@Controller
public class BudgetController {
    private final ResponseHandlerService responseHandler;

    @Autowired
    public BudgetController(ResponseHandlerService responseHandlerService) {
        this.responseHandler = responseHandlerService;
    }

    public Ability setBudget() {
        return Ability.builder().build();
    }

    public Ability editBudget() {
        return Ability.builder().build();
    }
}
