package brocodex.fbot.controller.bot;

import brocodex.fbot.handler.ResponseHandler;
import org.telegram.abilitybots.api.objects.Ability;


public class ReportController {
    private final ResponseHandler responseHandler;

    public ReportController(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public Ability generateReport() {
        return Ability.builder().build();
    }
}
