package brocodex.fbot.service.MQ;

import brocodex.fbot.handler.ResponseHandler;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageConsumer {
    @Setter
    private ResponseHandler handler;

    @RabbitListener(queues = "update_queue")
    public void receiveTransactionsMessage(Update update) {
        handler.handleUpdate(update);
    }
}
