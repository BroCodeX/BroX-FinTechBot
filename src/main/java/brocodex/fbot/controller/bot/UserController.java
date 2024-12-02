package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.dto.user.UserDTO;
import brocodex.fbot.handler.ResponseHandler;
import brocodex.fbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


public class UserController {

    @Autowired
    private UserService userService;

    private final ResponseHandler responseHandler;

    public UserController(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public void welcomeUser(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Welcome to the Finance Bot!\nPlease enter your name");
        responseHandler.getSender().execute(message);
        responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_USERNAME);
    }

    public void saveUsername(Long chatId, Message message) {
        UserDTO dto = new UserDTO();
        dto.setUsername(message.getText());
        dto.setTelegramId(message.getFrom().getId());

        var savedUser = userService.createUser(dto);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Hello " + savedUser.getUsername() + ". Please set your budget");

        responseHandler.getSender().execute(sendMessage);
        responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_BUDGET);
    }
}
