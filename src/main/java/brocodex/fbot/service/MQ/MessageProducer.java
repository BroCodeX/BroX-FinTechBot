package brocodex.fbot.service.MQ;

import brocodex.fbot.config.RabbitConfig;
import brocodex.fbot.constants.RoutingKeys;
import brocodex.fbot.dto.mq.MQDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate template;

    public void sendMessage(MQDTO dto, RoutingKeys routingKey) {
        template.convertAndSend(RabbitConfig.exchangeName, routingKey.getKey(), dto);
    }
}
