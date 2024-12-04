package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.dto.user.UserDTO;
import brocodex.fbot.service.ChatStateService;
import brocodex.fbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatStateService chatStateService;

    public SendMessage saveUsername(Long userId, String name, Long chatId) {
        UserDTO dto = new UserDTO();
        dto.setUsername(name);
        dto.setTelegramId(userId);

        var savedUser = userService.createUser(dto);
        chatStateService.removeChatState(chatId);
        return SendMessage
                .builder()
                .chatId(chatId)
                .text("Hello " + savedUser.getUsername() + ". Now you can add a budget")
                .build();
    }
}
