package brocodex.fbot.commands;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.controller.bot.UserController;
import brocodex.fbot.service.ChatStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AddTransactionCommand implements Command {
    @Autowired
    private ChatStateService stateService;

    @Autowired
    private UserController userController;

    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        long userId = update.getMessage().getFrom().getId();
        String userName = update.getMessage().getFrom().getUserName();
        String firstName = update.getMessage().getFrom().getFirstName();

        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(CommandMessages.WAIT_TRANSACTION_AMOUNT.getDescription())
                .build();

        stateService.setChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_AMOUNT);

        return sendMessage;
    }
}
