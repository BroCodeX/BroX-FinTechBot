package brocodex.fbot.handler;

import brocodex.fbot.controller.bot.TransactionsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Locale;

@Component
public class CallbackHandler {
    @Autowired
    private TransactionsController controller;

    public void handleCallback(Update update, TelegramClient telegramClient) {
        var chatId = update.getCallbackQuery().getMessage().getChatId();
        var userId = update.getCallbackQuery().getFrom().getId();
        var receivedMessage = update.getCallbackQuery().getData();
    }
}
