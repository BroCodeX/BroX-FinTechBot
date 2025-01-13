package brocodex.fbot.service.MQ;

import brocodex.fbot.config.RabbitConfig;
import brocodex.fbot.constants.RoutingKeys;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendTransactionMessage(String message, RoutingKeys routingKey) {
        amqpTemplate.convertAndSend(RabbitConfig.exchangeName, routingKey.getKey(), message);
        System.out.printf("Message sent with key:%s : message: %s", routingKey.getKey(), message);
    }
}
