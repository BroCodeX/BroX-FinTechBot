package brocodex.fbot.component;

import brocodex.fbot.handler.CallbackHandler;
import brocodex.fbot.handler.ResponseHandler;
import brocodex.fbot.controller.bot.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class FinanceBot extends TelegramLongPollingBot {

    @Autowired
    @Lazy
    private CallbackHandler callbackHandler;

    private final ResponseHandler responseHandler;

    @Value("${telegram.bot.name}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Autowired
    private TransactionsController transactionsController;

    @Autowired
    public FinanceBot(@Value("${telegram.bot.token}") String botToken,
                      @Value("${telegram.bot.name}") String botName,
                      ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> sendResponse(chatId, "Welcome! Use /help to see available commands.");
                case "/help" -> sendResponse(chatId, """
                    Available commands:
                    /start - Start the bot
                    /help - Show available commands
                    /add_transaction - Add a new transaction
                    /view_transactions - View your transactions
                    /edit_budget - Edit your budget
                    /view_budget - View your current budget
                    """);
                case "/add_transaction" -> responseHandler.replyToButtons(chatId, update.getMessage());
                default -> responseHandler.replyToButtons(chatId, update.getMessage());
            }
        } else if (update.hasCallbackQuery()) {
            callbackHandler.handleCallback(update.getCallbackQuery());
        }
    }

    private void sendResponse(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
