package brocodex.fbot.handler;

import brocodex.fbot.commands.Command;
import brocodex.fbot.constants.ChatState;
import brocodex.fbot.service.ChatStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ResponseHandler {
    @Autowired
    private ChatStateService chatStateService;

    private TelegramClient telegramClient;

    @Autowired
    private StateHandler stateHandler;

    private final Map<String, Command> commandMap;

    public ResponseHandler (Command startCommand,
                            Command helpCommand,
                            Command addTransactionCommand) {
        this.commandMap = Map.of(
                "/start", startCommand,
                "/help", helpCommand,
                "/add_transaction", addTransactionCommand
        );
    }

    public void handleUpdate(Update update, TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            botAnswerUtils(update);
        } else if (update.hasCallbackQuery()) {
            String receivedMessage = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            long userId = update.getCallbackQuery().getFrom().getId();
            String userName = update.getMessage().getFrom().getUserName();

            //тут будет callbackHandler
        }
    }

    private void botAnswerUtils(Update update) {
        String receivedMessage = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        if(!commandMap.containsKey(receivedMessage) && chatStateService.getChatState(chatId) == null) {
            handleUnexpectedMessage(update);
        } else if(!commandMap.containsKey(receivedMessage) && chatStateService.getChatState(chatId) != null) {
            stateHandler.handleState(update, telegramClient);
        } else {
            var command = commandMap.get(receivedMessage);
            var message = command.apply(update);
            sendMessage(message);
        }
        // тут надо продумать как callback
    }

    public void handleUnexpectedMessage(Update update) {
        long userId = update.getMessage().getFrom().getId();
        long chatId = update.getMessage().getChatId();
        String receivedMessage = update.getMessage().getText();

        StringBuilder builder = new StringBuilder();
        builder.append("I don't understand your message: ").append(receivedMessage).append(" \n");
        builder.append("You can type /help for assistance.");
        SendMessage sendMessage = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(builder.toString())
                .build();
        sendMessage(sendMessage);
        log.info(receivedMessage);
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}
