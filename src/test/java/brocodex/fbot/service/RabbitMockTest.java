package brocodex.fbot.service;

import brocodex.fbot.constants.RoutingKeys;
import brocodex.fbot.handler.ResponseHandler;
import brocodex.fbot.service.MQ.MessageConsumer;
import brocodex.fbot.service.MQ.MessageProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RabbitMockTest {
    private final CountDownLatch latch = new CountDownLatch(1);
    private String receivedMessage;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ResponseHandler handler;

    @InjectMocks
    private MessageConsumer consumer;

    @InjectMocks
    private MessageProducer producer;

    @BeforeEach
    public void prepare() {
        consumer.setHandler(handler);
    }

    @Test
    public void testSendMessage() throws InterruptedException{
        String message = "Yandex test message";
        String exchange = "update_exchange";
        String routingKey = RoutingKeys.UPDATE.getKey();

        var upd = new Update();
        upd.setMessage(Message.builder().text(message).build());

        doNothing().when(rabbitTemplate).convertAndSend(eq(exchange), eq(routingKey), eq(upd));
        producer.sendUpdateMessage(upd, RoutingKeys.UPDATE);
        verify(rabbitTemplate, times(1)).convertAndSend(eq(exchange), eq(routingKey),
                any(Update.class));

        consumer.receiveTransactionsMessage(upd);
        verify(handler, times(1)).handleUpdate(upd);

        receivedMessage = upd.getMessage().getText();
        latch.countDown();


        boolean messageReceived = latch.await(10, TimeUnit.SECONDS);

        assertThat(messageReceived).isTrue();
        assertThat(message).isEqualTo(receivedMessage);
    }

}
