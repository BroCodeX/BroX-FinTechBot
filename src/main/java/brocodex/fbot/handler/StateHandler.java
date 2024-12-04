package brocodex.fbot.handler;

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
    private UserController userController;

    private TelegramClient telegramClient;

    public void handleState(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        var actualState = chatStateService.getChatState(chatId);
        String message = update.getMessage().getText();

        switch (actualState) {
            case WAITING_FOR_USERNAME -> {
                var result = userController.saveUsername(chatId, message);
                sendMessage(result);
            }
//            case WAITING_FOR_BUDGET -> budgetController.setBudget(chatID, message);
//            case READY -> infoMessage(chatID, message);
//            case WAITING_FOR_TRANSACTION_AMOUNT -> transactionsController.addTransactionAmount(chatID, message);
//            case WAITING_FOR_TRANSACTION_TYPE -> transactionsController.sendTransactionTypeButtons(chatID);
//            case WAITING_FOR_TRANSACTION_DESCRIPTION ->
//                    transactionsController.addTransactionDescription(chatID, message);
//            case WAITING_FOR_TRANSACTION_CATEGORY ->
//                    transactionsController.addTransactionCategory(chatID, message);
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
