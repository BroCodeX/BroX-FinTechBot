package brocodex.fbot.service.handler;

import brocodex.fbot.controller.bot.TransactionsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Locale;

@Service
public class CallbackHandlerService {
    @Autowired
    private TransactionsController controller;
    public void handleCallback(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String maybeType = callbackQuery.getData().toLowerCase(Locale.ROOT).trim();

        controller.addTransactionType(chatId, maybeType);
    }
}
