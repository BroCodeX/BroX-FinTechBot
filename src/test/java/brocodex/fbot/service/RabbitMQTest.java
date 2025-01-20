package brocodex.fbot.service;

import brocodex.fbot.constants.RoutingKeys;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final CountDownLatch latch = new CountDownLatch(1);

    private String receivedMessage;

    @RabbitListener(queues = "update_queue")
    public void receiveMessage(Update receivedMessage) {
        this.receivedMessage = receivedMessage.getMessage().getText();
        latch.countDown();
    }

    @Test
    public void testSendMessage() throws InterruptedException {
        String message = "Yandex test message";
        String exchange = "update_exchange";
        String routingKey = RoutingKeys.UPDATE.getKey();

        var upd = new Update();
        upd.setMessage(Message.builder().text(message).build());

        amqpTemplate.convertAndSend(exchange, routingKey, upd);

        boolean messageReceived = latch.await(10, TimeUnit.SECONDS);

        assertThat(messageReceived).isTrue();
        assertThat(message).isEqualTo(receivedMessage);
    }
}
