package brocodex.fbot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    public static String updateMessageQueue = "update_queue";
    public static String exchangeName = "update_exchange";

    @Bean
    public TopicExchange transactionsExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue transactionsQueue() {
        return new Queue(updateMessageQueue, true);
    }

    @Bean
    public Binding transactionsBinding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("update.#");
    }
}
