package brocodex.fbot.service.handler;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.controller.bot.BudgetController;
import brocodex.fbot.controller.bot.TransactionsController;
import brocodex.fbot.controller.bot.UserController;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

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

        switch (chatStates.get(chatID)) {
            case WAITING_FOR_USERNAME -> userController.saveUsername(chatID, message);
            case WAITING_FOR_BUDGET -> budgetController.setBudget(chatID, message);
//            case READY -> ;
            case WAITING_FOR_TRANSACTION -> transactionsController.addTransaction(chatID, message);
            case TRANSACTION_REPORT_FILTERS -> ;
//            case ADMIN_MODE -> ;
            default -> unexpectedMessage(chatID, message.getText());
        }
    }

    public void stopChat(long chatId) {

    }

    public void unexpectedMessage(long chatId, String userMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("I don't understand your message: " + userMessage);
        sender.execute(message);
    }

    public boolean userIsActive(Long chatId) {
        return chatStates.containsKey(chatId);
    }
}
