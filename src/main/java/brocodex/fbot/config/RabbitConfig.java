package brocodex.fbot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    public static String transactionsQueue = "transactions_queue";
    public static String budgetQueue = "budget_queue";
    public static String exchangeName = "transactions_exchange";

    @Bean
    public TopicExchange transactionsExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue transactionsQueue() {
        return new Queue(transactionsQueue, true);
    }

    @Bean
    public Queue budgetQueue() {
        return new Queue(budgetQueue, true);
    }

    @Bean
    public Binding transactionsBinding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("transactions.#");
    }

    @Bean
    public Binding budgetBinding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("budget.#");
    }
}
