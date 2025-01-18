package ru.vladuss.gamesservice.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // Конвертер сообщений JSON
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Конфигурация RabbitListener
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
        return factory;
    }

    // Exchange
    @Bean
    public DirectExchange gameExchange() {
        return new DirectExchange("gamesExchange");
    }

    // CREATE
    @Bean
    public Queue createGameQueue() {
        return new Queue("createGameQueue", true);
    }

    @Bean
    public Binding createGameBinding(Queue createGameQueue, DirectExchange gameExchange) {
        return BindingBuilder.bind(createGameQueue).to(gameExchange).with("game.create.key");
    }

    // UPDATE
    @Bean
    public Queue updateGameQueue() {
        return new Queue("updateGameQueue", true);
    }

    @Bean
    public Binding updateGameBinding(Queue updateGameQueue, DirectExchange gameExchange) {
        return BindingBuilder.bind(updateGameQueue).to(gameExchange).with("game.update.key");
    }

    // DELETE
    @Bean
    public Queue deleteGameQueue() {
        return new Queue("deleteGameQueue", true);
    }

    @Bean
    public Binding deleteGameBinding(Queue deleteGameQueue, DirectExchange gameExchange) {
        return BindingBuilder.bind(deleteGameQueue).to(gameExchange).with("game.delete.key");
    }
}
