package brocodex.fbot.component;

import brocodex.fbot.constants.RoutingKeys;
import brocodex.fbot.dto.mq.MQDTO;
import brocodex.fbot.handler.ResponseHandler;
import brocodex.fbot.handler.StateHandler;
import brocodex.fbot.service.MQ.MessageConsumer;
import brocodex.fbot.service.MQ.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
public class FinanceBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private String token;

    private final TelegramClient telegramClient;

    private final ResponseHandler responseHandler;

    private final StateHandler stateHandler;

    @Autowired
    private MessageProducer producer;

    private final MessageConsumer consumer;

    @Autowired
    public FinanceBot(ResponseHandler responseHandler, MessageConsumer consumer,
                      @Value("${telegram.bot.token}") String token, StateHandler stateHandler) {
        this.token = token;
        telegramClient = new OkHttpTelegramClient(getBotToken());
        this.responseHandler = responseHandler;
        this.responseHandler.setTelegramClient(telegramClient);
        this.stateHandler = stateHandler;
        this.stateHandler.setTelegramClient(telegramClient);
        this.consumer = consumer;
        this.consumer.setHandler(responseHandler);
        this.consumer.setStateHandler(stateHandler);
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        MQDTO dto = new MQDTO();
//        responseHandler.handleUpdate(update);
//        producer.sendUpdateMessage(update, RoutingKeys.UPDATE);
        if (update.hasMessage() && update.getMessage().hasText()) {
            dto.setChatId(update.getMessage().getChatId());
            dto.setUserId(update.getMessage().getFrom().getId());
            dto.setMessage(update.getMessage().getText());
            dto.setUserName(update.getMessage().getFrom().getUserName());
            dto.setFirstName(update.getMessage().getFrom().getFirstName());
            producer.sendMessage(dto, RoutingKeys.DIRECT);
        }
        if (update.hasCallbackQuery()) {
            dto.setChatId(update.getCallbackQuery().getMessage().getChatId());
            dto.setUserId(update.getCallbackQuery().getFrom().getId());
            dto.setMessage(update.getCallbackQuery().getData());
            dto.setUserName(update.getCallbackQuery().getFrom().getUserName());
            dto.setFirstName(update.getCallbackQuery().getFrom().getFirstName());
            producer.sendMessage(dto, RoutingKeys.CALLBACK);
        }
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Registered bot running state is: " + botSession.isRunning());
    }
}
