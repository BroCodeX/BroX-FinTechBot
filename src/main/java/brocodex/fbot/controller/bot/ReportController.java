package brocodex.fbot.controller.bot;

import brocodex.fbot.service.handler.ResponseHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.abilitybots.api.objects.Ability;


public class ReportController {
    private final ResponseHandlerService responseHandler;

    public ReportController(ResponseHandlerService responseHandlerService) {
        this.responseHandler = responseHandlerService;
    }

    public Ability generateReport() {
        return Ability.builder().build();
    }
}
