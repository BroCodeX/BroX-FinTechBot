package brocodex.fbot.handler;

import brocodex.fbot.controller.bot.BudgetController;
import brocodex.fbot.controller.bot.TransactionsController;
import brocodex.fbot.controller.bot.UserController;
import brocodex.fbot.service.ChatStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
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

    public void handleState(Update update, TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
        long chatId = update.getMessage().getChatId();
        var actualState = chatStateService.getChatState(chatId);
        long userId = update.getMessage().getFrom().getId();
        String receivedMessage = update.getMessage().getText();

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
                transactionsController.sendTransactionTypeButtons();
            }
            case WAITING_FOR_TRANSACTION_DESCRIPTION -> {
                transactionsController.addTransactionDescription(chatId, receivedMessage, userId);
            }
            case WAITING_FOR_TRANSACTION_CATEGORY -> {
                transactionsController.addTransactionCategory(chatId, receivedMessage, userId);
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
