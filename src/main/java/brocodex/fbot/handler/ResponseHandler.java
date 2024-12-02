package brocodex.fbot.handler;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.controller.bot.BudgetController;
import brocodex.fbot.controller.bot.TransactionsController;
import brocodex.fbot.controller.bot.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ResponseHandler {

    private final TelegramLongPollingBot bot;
    private final Map<Long, ChatState> chatStates = new HashMap<>();
    private final UserController userController;
    private final BudgetController budgetController;
    private final TransactionsController transactionsController;

    public ResponseHandler(TelegramLongPollingBot bot,
                           UserController userController,
                           BudgetController budgetController,
                           TransactionsController transactionsController) {
        this.bot = bot;
        this.userController = userController;
        this.budgetController = budgetController;
        this.transactionsController = transactionsController;
    }

    public void handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            // Обработка коллбэков
            // пример: callbackHandler.handleCallback(update.getCallbackQuery());
        }
    }

    private void handleMessage(Message message) {
        Long chatId = message.getChatId();
        String messageText = message.getText();

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
            case "/add_transaction" -> transactionsController.replyToAddTransaction(chatId, message.getFrom().getId());
            default -> replyToButtons(chatId, message);
        }
    }

    public void replyToButtons(long chatId, Message message) {
        if ("/stop".equals(message.getText())) {
            stopChat(chatId);
            return;
        }

        chatStates.putIfAbsent(chatId, ChatState.READY);
        log.warn("ChatId {} was null. Defaulting to READY", chatId);

        switch (chatStates.get(chatId)) {
            case WAITING_FOR_USERNAME -> userController.saveUsername(chatId, message);
            case WAITING_FOR_BUDGET -> budgetController.setBudget(chatId, message);
            case READY -> infoMessage(chatId, message);
            case WAITING_FOR_TRANSACTION_AMOUNT -> transactionsController.addTransactionAmount(chatId, message);
            case WAITING_FOR_TRANSACTION_TYPE -> transactionsController.sendTransactionTypeButtons(chatId);
            case WAITING_FOR_TRANSACTION_DESCRIPTION -> transactionsController.addTransactionDescription(chatId, message);
            case WAITING_FOR_TRANSACTION_CATEGORY -> transactionsController.addTransactionCategory(chatId, message);
            default -> handleUnexpectedState(chatId, chatStates.get(chatId));
        }
    }

    public void stopChat(long chatId) {
        sendResponse(chatId, "Thanks for using our bot. See you soon and have a nice day!");
        chatStates.remove(chatId);
    }

    public void handleUnexpectedState(long chatId, ChatState state) {
        log.error("Unexpected state {} for chatID {}", state, chatId);
        updateChatState(chatId, ChatState.READY);
        sendResponse(chatId, "An unexpected error occurred. Please try again or type /help for assistance.");
    }

    public void infoMessage(long chatId, Message userMessage) {
        sendResponse(chatId, "I don't understand your message:\nYou can type /help for assistance.");
    }

    public void updateChatState(Long chatId, ChatState state) {
        chatStates.put(chatId, state);
    }

    public boolean userIsActive(Long chatId) {
        return chatStates.containsKey(chatId);
    }

    private void sendResponse(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error sending message to {}: {}", chatId, e.getMessage());
        }
    }
}
