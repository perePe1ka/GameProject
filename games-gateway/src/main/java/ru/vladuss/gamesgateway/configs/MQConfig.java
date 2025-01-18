package ru.vladuss.gamesgateway.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

    // Exchange
    @Bean
    public DirectExchange gamesExchange() {
        return new DirectExchange("gamesExchange");
    }

    // Queues
    @Bean
    public Queue createQueue() {
        return new Queue("createGameQueue");
    }

    @Bean
    public Queue updateQueue() {
        return new Queue("updateGameQueue");
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue("deleteGameQueue");
    }

    // Bindings
    @Bean
    public Binding createBinding(Queue createQueue, DirectExchange gamesExchange) {
        return BindingBuilder.bind(createQueue).to(gamesExchange).with("game.create.key");
    }

    @Bean
    public Binding updateBinding(Queue updateQueue, DirectExchange gamesExchange) {
        return BindingBuilder.bind(updateQueue).to(gamesExchange).with("game.update.key");
    }

    @Bean
    public Binding deleteBinding(Queue deleteQueue, DirectExchange gamesExchange) {
        return BindingBuilder.bind(deleteQueue).to(gamesExchange).with("game.delete.key");
    }
}
