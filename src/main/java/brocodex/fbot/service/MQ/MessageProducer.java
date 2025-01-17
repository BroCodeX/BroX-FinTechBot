package brocodex.fbot.service.MQ;

import brocodex.fbot.config.RabbitConfig;
import brocodex.fbot.constants.RoutingKeys;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate template;

    public void sendUpdateMessage(Update message, RoutingKeys routingKey) {
        template.convertAndSend(RabbitConfig.exchangeName, routingKey.getKey(), message);
    }
}
