package brocodex.fbot.commands;

import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.dto.mq.MQDTO;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Help implements Command {

    @Override
    public SendMessage apply(MQDTO dto) {
        String text = CommandMessages.HELP_MESSAGE.getDescription();

        long chatId = dto.getChatId();

        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .build();

        return sendMessage;
    }
}
