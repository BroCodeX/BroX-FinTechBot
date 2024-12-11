package brocodex.fbot.handler;

import brocodex.fbot.controller.bot.BudgetController;
import brocodex.fbot.controller.bot.TransactionsController;
import brocodex.fbot.controller.bot.UserController;
import brocodex.fbot.service.ChatStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class StateHandler {
    @Autowired
    private ChatStateService chatStateService;

    @Autowired
    private BudgetController budgetController;

    @Autowired
    private TransactionsController transactionsController;

    private TelegramClient telegramClient;

    public void handleState(String receivedMessage, TelegramClient telegramClient, Long chatId, Long userId) {
        this.telegramClient = telegramClient;
        var actualState = chatStateService.getChatState(chatId);

        switch (actualState) {
            case WAITING_FOR_BUDGET -> {
                var message = budgetController.setBudget(chatId, receivedMessage, userId);
                sendMessage(message);
            }
            case WAITING_FOR_TRANSACTION_AMOUNT -> {
                var message = transactionsController.addTransactionAmount(chatId, receivedMessage, userId);
                sendMessage(message);
            }
            case WAITING_FOR_TRANSACTION_TYPE -> {
                var message = transactionsController.addTransactionType(chatId, receivedMessage);
                sendMessage(message);
            }
            case WAITING_FOR_TRANSACTION_CATEGORY -> {
                var message = transactionsController.addTransactionCategory(chatId, receivedMessage);
                sendMessage(message);
            }
            case WAITING_FOR_TRANSACTION_DESCRIPTION -> {
                var message = transactionsController.addTransactionDescription(chatId, receivedMessage);
                sendMessage(message);
            }
//            case READY -> infoMessage(chatID, message);
//            case TRANSACTION_REPORT_FILTERS -> ;
//            case ADMIN_MODE -> ;
            default -> chatStateService.removeChatState(chatId);
        }
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}
