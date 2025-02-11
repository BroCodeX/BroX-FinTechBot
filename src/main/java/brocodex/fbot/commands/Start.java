package brocodex.fbot.commands;

import brocodex.fbot.dto.mq.MQDTO;
import brocodex.fbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Start implements Command {

    @Autowired
    private UserService userService;

    @Override
    public SendMessage apply(MQDTO dto) {
        return userService.welcomeUser(dto);
    }
}
