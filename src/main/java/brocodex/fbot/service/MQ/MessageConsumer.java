package brocodex.fbot.service.MQ;

import brocodex.fbot.dto.mq.MQDTO;
import brocodex.fbot.handler.ResponseHandler;
import brocodex.fbot.handler.StateHandler;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageConsumer {
    @Setter
    private ResponseHandler handler;

    @Setter
    private StateHandler stateHandler;

    @RabbitListener(queues = "direct_queue")
    public void transactionsMessage(MQDTO dto) {
        handler.botAnswerUtils(dto);
    }

    @RabbitListener(queues = "callback_queue")
    public void callbackTransactionsMessage(MQDTO dto) {
        stateHandler.handleState(dto);
    }
}
