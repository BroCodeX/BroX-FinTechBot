package brocodex.fbot.commands;

import brocodex.fbot.dto.mq.MQDTO;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    SendMessage apply(MQDTO mqdto);
}
