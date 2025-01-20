package ru.vladuss.gamesservice.services.impl;

import com.example.gameproto.GameResponse;
import com.example.gameproto.DeleteGameMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;
import ru.vladuss.gamesservice.dtos.GameDto;
import ru.vladuss.gamesservice.entityes.Game;
import ru.vladuss.gamesservice.services.GameService;

@Component
public class MQListener {
    private final GameService gameService;

    @Autowired
    public MQListener(GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues = "createGameQueue")
    private void createGame(GameResponse response) {
        Game game = new Game(
                null,
                response.getName(),
                response.getGenre(),
                response.getPlatform()
        );
        gameService.createGame(game);
    }

    @RabbitListener(queues = "updateGameQueue")
    public void updateGame(GameResponse response){

        if (response.getId() == null || response.getId().isEmpty()) {
            throw new IllegalArgumentException("Айди игры не может быть нулевым или пустым.");
        }

        Game game = new Game(
                response.getId(),
                response.getName(),
                response.getGenre(),
                response.getPlatform()
        );
        gameService.updateGame(game);
    }

    @RabbitListener(queues = "deleteGameQueue")
    public void deleteGame(DeleteGameMessage deleteGameMessage) {
        if (deleteGameMessage != null) {
            gameService.deleteGame(deleteGameMessage.getId());
        }
    }

}
