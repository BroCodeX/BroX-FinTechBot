package brocodex.fbot.commands;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.dto.user.UserDTO;
import brocodex.fbot.service.ChatStateService;
import brocodex.fbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Start implements Command {
    @Autowired
    private ChatStateService stateService;

    @Autowired
    private UserService userService;

    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        long userId = update.getMessage().getFrom().getId();
        String userName = update.getMessage().getFrom().getUserName();
        String firstName = update.getMessage().getFrom().getFirstName();

        UserDTO dto = new UserDTO();
        dto.setUsername(userName);
        dto.setTelegramId(userId);

        if(userService.isUserPresent(userId)) {
            return SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text(CommandMessages.HELP_MESSAGE.getDescription())
                    .build();
        }

        userService.createUser(dto);

        String text = CommandMessages.START_MESSAGE.getDescription() +
                firstName +
                "\n" +
                "Please, set your budget or type /help to show the commands";

        SendMessage sendMessage = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(text)
                .build();

        stateService.setChatState(chatId, ChatState.WAITING_FOR_BUDGET);

        return sendMessage;
    }
}
