package brocodex.fbot.handler;

import brocodex.fbot.commands.*;
import brocodex.fbot.dto.mq.MQDTO;
import brocodex.fbot.service.ChatStateService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Map;

@Slf4j
@Component
public class ResponseHandler {
    @Autowired
    private ChatStateService chatState;

    @Setter
    private TelegramClient telegramClient;

    @Autowired
    private StateHandler stateHandler;

    private final Map<String, Command> commandMap;

    public ResponseHandler (Start start,
                            Help help,
                            AddTransaction addTransaction,
                            ViewBudget viewBudget,
                            SetNewBudget setNewBudget,
                            GetReport getReport) {
        this.commandMap = Map.of(
                "/start", start,
                "/help", help,
                "/add_transaction", addTransaction,
                "/view_budget", viewBudget,
                "/edit_budget", setNewBudget,
                "/get_report", getReport
        );
    }

    public void botAnswerUtils(MQDTO dto) {
        String receivedMessage = dto.getMessage();
        long chatId = dto.getChatId();
        if(!commandMap.containsKey(receivedMessage) && chatState.getChatState(chatId) == null) {
            handleUnexpectedMessage(dto);
        } else if(!commandMap.containsKey(receivedMessage) && chatState.getChatState(chatId) != null) {
            stateHandler.handleState(dto);
        } else {
            var command = commandMap.get(receivedMessage);
            var message = command.apply(dto);
            sendMessage(message);
        }
    }

    public void handleUnexpectedMessage(MQDTO dto) {
        long chatId = dto.getChatId();
        String receivedMessage = dto.getMessage();

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
