package brocodex.fbot.commands;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.service.ChatStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AddTransaction implements Command {
    @Autowired
    private ChatStateService stateService;

    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();

        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(CommandMessages.WAIT_TRANSACTION_AMOUNT.getDescription())
                .build();

        stateService.setChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_AMOUNT);

        return sendMessage;
    }
}
