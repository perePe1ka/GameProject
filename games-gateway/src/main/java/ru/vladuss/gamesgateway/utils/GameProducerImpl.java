package ru.vladuss.gamesgateway.utils;

import com.example.gameproto.DeleteGameMessage;
import com.example.gameproto.GameResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vladuss.gamesgateway.services.impl.GameServiceImpl;

@Component
public class GameProducerImpl {
    private final AmqpTemplate template;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);

    public GameProducerImpl(AmqpTemplate template) {
        this.template = template;
    }

    @Value("${app.rabbit.exchange}")
    private String exchange;

    @Value("${app.rabbit.create-routing-key}")
    private String createRouteKey;

    @Value("${app.rabbit.update-routing-key}")
    private String updateRouteKey;

    @Value("${app.rabbit.delete-routing-key}")
    private String deleteRouteKey;

    public void sendGameCreated(GameResponse gameDto) {
        LOGGER.info("Отправлено сообщение на создание");
        template.convertAndSend(exchange, createRouteKey, gameDto);
    }

    public void sendGameUpdated(GameResponse gameDto) {
        LOGGER.info("Отправлено сообщение на изменение");
        template.convertAndSend(exchange, updateRouteKey, gameDto);
    }

    public void sendGameDelete(String id) {
        LOGGER.info("Отправлено сообщение на удаление");
        DeleteGameMessage deleteGameMessage = DeleteGameMessage.newBuilder()
                .setId(id)
                .build();
        template.convertAndSend(exchange, deleteRouteKey, deleteGameMessage);
    }
}
