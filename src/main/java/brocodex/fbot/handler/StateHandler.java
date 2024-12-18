package brocodex.fbot.handler;

import brocodex.fbot.controller.bot.TransactionsController;
import brocodex.fbot.service.BudgetService;
import brocodex.fbot.service.ChatStateService;
import brocodex.fbot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class StateHandler {
    @Autowired
    private ChatStateService chatStateService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private TransactionsController transactionsController;

    @Autowired
    private ReportService reportService;

    private TelegramClient telegramClient;

    public void handleState(String receivedMessage, TelegramClient telegramClient, Long chatId, Long userId) {
        this.telegramClient = telegramClient;
        var actualState = chatStateService.getChatState(chatId);

        switch (actualState) {
            case WAITING_FOR_BUDGET -> {
                var message = budgetService.setBudget(chatId, receivedMessage, userId);
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
            case WAITING_FOR_DATA_FILTERS -> {
                var message = reportService.addDataFilter(chatId, userId, receivedMessage);
                sendMessage(message);
            }
            case WAITING_FOR_TYPE_FILTERS -> {
                var message = reportService.addTypeFilter(chatId, receivedMessage);
                sendMessage(message);
            }
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
