package brocodex.fbot.controller.bot;

import brocodex.fbot.service.handler.ResponseHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.meta.api.objects.Message;

@Controller
public class TransactionsController {
    private final ResponseHandlerService responseHandler;

    @Autowired
    public TransactionsController(ResponseHandlerService responseHandlerService) {
        this.responseHandler = responseHandlerService;
    }

    public void replyToAddTransaction(Long chatId) {
    }

    public void addTransaction(Long chatId, Message message) {
    }

    public Ability viewTransactions() {
        return Ability.builder().build();
    }

}
