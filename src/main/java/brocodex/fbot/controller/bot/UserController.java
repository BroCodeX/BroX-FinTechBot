package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.dto.user.UserDTO;
import brocodex.fbot.service.ChatStateService;
import brocodex.fbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatStateService chatStateService;

    public void saveUsername(Long userId, String name, Long chatId) {
        UserDTO dto = new UserDTO();
        dto.setUsername(name);
        dto.setTelegramId(userId);

        userService.createUser(dto);
        chatStateService.setChatState(chatId, ChatState.WAITING_FOR_BUDGET);
    }
}
