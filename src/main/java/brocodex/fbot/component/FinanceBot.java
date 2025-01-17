package brocodex.fbot.component;

import brocodex.fbot.constants.RoutingKeys;
import brocodex.fbot.handler.ResponseHandler;
import brocodex.fbot.service.MQ.MessageConsumer;
import brocodex.fbot.service.MQ.MessageProducer;
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

@Component
public class FinanceBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private String token;

    private final TelegramClient telegramClient;

    private final ResponseHandler responseHandler;

    @Autowired
    private MessageProducer producer;

    private final MessageConsumer consumer;

    @Autowired
    public FinanceBot(ResponseHandler responseHandler, MessageConsumer consumer,
                      @Value("${telegram.bot.token}") String token) {
        this.token = token;
        telegramClient = new OkHttpTelegramClient(getBotToken());
        this.responseHandler = responseHandler;
        this.responseHandler.setTelegramClient(telegramClient);
        this.consumer = consumer;
        this.consumer.setHandler(responseHandler);
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
//        responseHandler.handleUpdate(update);
        producer.sendUpdateMessage(update, RoutingKeys.UPDATE);
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Registered bot running state is: " + botSession.isRunning());
    }
}
