package brocodex.fbot.service.handler;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.controller.bot.BudgetController;
import brocodex.fbot.controller.bot.TransactionsController;
import brocodex.fbot.controller.bot.UserController;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Slf4j
@Service
@Getter
public class ResponseHandlerService {
    private final SilentSender sender;
    private final Map<Long, ChatState> chatStates;

    @Autowired
    private UserController userController;
    @Autowired
    private BudgetController budgetController;
    @Autowired
    private TransactionsController transactionsController;

    public ResponseHandlerService(SilentSender sender, DBContext dbContext) {
        this.sender = sender;
        this.chatStates = dbContext.getMap("CHAT_STATES");
    }

    public void updateChatState(Long chatId, ChatState state) {
        chatStates.put(chatId, state);
    }

    public void replyToButtons(long chatID, Message message) {
        if (message.getText().equals("/stop")) {
            stopChat(chatID);
        }

        if(chatStates.get(chatID) == null) {
            updateChatState(chatID, ChatState.READY);
            log.warn("ChatId {} was null. Defaulting to READY", chatID);
            log.info(ChatState.READY.getDescription());
        }

        switch (chatStates.get(chatID)) {
            case WAITING_FOR_USERNAME -> userController.saveUsername(chatID, message);
            case WAITING_FOR_BUDGET -> budgetController.setBudget(chatID, message);
            case READY -> infoMessage(chatID, message);
            case WAITING_FOR_TRANSACTION_AMOUNT -> transactionsController.addTransactionAmount(chatID, message);
            case WAITING_FOR_TRANSACTION_TYPE -> transactionsController.sendTransactionTypeButtons(chatID);
            case WAITING_FOR_TRANSACTION_DESCRIPTION ->
                    transactionsController.addTransactionDescription(chatID, message);
            case WAITING_FOR_TRANSACTION_CATEGORY ->
                    transactionsController.addTransactionCategory(chatID, message);
//            case TRANSACTION_REPORT_FILTERS -> ;
//            case ADMIN_MODE -> ;
            default -> handleUnexpectedState(chatID, chatStates.get(chatID));
        }
    }

    public void stopChat(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Thanks for using our bot. See you soon and have a nice day!");

        chatStates.remove(chatId);
        sender.execute(sendMessage);
    }

    public void handleUnexpectedState(long chatId, ChatState state) {
        log.error("Unexpected state {} for chatID {}", state, chatId);

        updateChatState(chatId, ChatState.READY);

        SendMessage errorMessage = new SendMessage();
        errorMessage.setChatId(chatId);
        errorMessage.setText("An unexpected error occurred. Please try again or type /help for assistance.");

        sender.execute(errorMessage);
    }

    public void infoMessage(long chatId, Message userMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("I don't understand your message:\n" +
                "You can type /help for assistance.");
        sender.execute(message);
    }

    public boolean userIsActive(Long chatId) {
        return chatStates.containsKey(chatId);
    }
}
