package brocodex.fbot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    public static String directMessageQueue = "direct_queue";
    public static String callbackMessageQueue = "callback_queue";
    public static String exchangeName = "message_exchange";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange transactionsExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue directQueue() {
        return new Queue(directMessageQueue, true);
    }

    @Bean
    public Queue callbackQueue() {
        return new Queue(callbackMessageQueue, true);
    }

    @Bean
    public Binding directBinding(@Qualifier("directQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("direct.#");
    }

    @Bean
    public Binding callbackBinding(@Qualifier("callbackQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("callback.#");
    }
}
