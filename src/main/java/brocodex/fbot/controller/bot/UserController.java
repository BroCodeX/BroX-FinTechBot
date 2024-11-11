package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.service.handler.ResponseHandlerService;
import brocodex.fbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    private final ResponseHandlerService responseHandler;

    @Autowired
    public UserController(ResponseHandlerService responseHandlerService) {
        this.responseHandler = responseHandlerService;
    }

    public Ability start() {
        return Ability.builder()
                .name("start")
                .info("Starts the bot")
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    Long chatId = ctx.chatId();
                    welcomeUser(chatId);
                })
                .build();
    }

    public void welcomeUser(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Welcome to the Finance Bot!\nPlease enter your name");
        responseHandler.getSender().execute(message);
        responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_USERNAME);
    }
}
