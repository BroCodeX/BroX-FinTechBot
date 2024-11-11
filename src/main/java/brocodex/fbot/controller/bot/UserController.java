package brocodex.fbot.controller.bot;

import brocodex.fbot.handler.ResponseHandler;
import org.telegram.abilitybots.api.objects.Ability;

public class UserController {
    private final ResponseHandler responseHandler;

    public UserController(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public Ability start() {
        return Ability.builder().build();
    }
}
