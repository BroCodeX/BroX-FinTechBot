package brocodex.fbot.commands;

import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.handler.ResponseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpCommand implements Command {

    @Override
    public SendMessage apply(Update update) {
        String text = CommandMessages.HELP_MESSAGE.getDescription();

        long chatId = update.getMessage().getChatId();

        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .build();

        return sendMessage;
    }
}
