package ru.vladuss.gamesservice.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vladuss.gamesservice.utils.ProtoMessConverter;

@Configuration
public class RabbitConfig {
    @Bean
    public MessageConverter protobufMessageConverter() {
        return new ProtoMessConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter protobufMessageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(protobufMessageConverter);
        return factory;
    }

    @Bean
    public DirectExchange gameExchange() {
        return new DirectExchange("gamesExchange");
    }

    @Bean
    public Queue createGameQueue() {
        return new Queue("createGameQueue", true);
    }

    @Bean
    public Binding createGameBinding(Queue createGameQueue, DirectExchange gameExchange) {
        return BindingBuilder.bind(createGameQueue).to(gameExchange).with("game.create.key");
    }

    @Bean
    public Queue updateGameQueue() {
        return new Queue("updateGameQueue", true);
    }

    @Bean
    public Binding updateGameBinding(Queue updateGameQueue, DirectExchange gameExchange) {
        return BindingBuilder.bind(updateGameQueue).to(gameExchange).with("game.update.key");
    }

    @Bean
    public Queue deleteGameQueue() {
        return new Queue("deleteGameQueue", true);
    }

    @Bean
    public Binding deleteGameBinding(Queue deleteGameQueue, DirectExchange gameExchange) {
        return BindingBuilder.bind(deleteGameQueue).to(gameExchange).with("game.delete.key");
    }
}
