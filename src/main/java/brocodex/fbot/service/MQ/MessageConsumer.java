package brocodex.fbot.service.MQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = "transactions_queue")
    public void receiveTransactionsMessage(String message) {
        System.out.println("Received message: " + message);
        // Логика обработки сообщения
    }

    @RabbitListener(queues = "budget_queue")
    public void receiveBudgetMessage(String message) {
        System.out.println("Received message: " + message);
        // Логика обработки сообщения
    }
}
