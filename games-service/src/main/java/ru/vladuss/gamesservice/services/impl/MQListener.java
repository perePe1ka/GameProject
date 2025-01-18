package ru.vladuss.gamesservice.services.impl;

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

    @RabbitListener(queues = "createGame")
    private void createGame(GameDto gameDto) {
        Game game = new Game(
                null,
                gameDto.getName(),
                gameDto.getGenre(),
                gameDto.getPlatform()
        );
        gameService.createGame(game);
    }

    @RabbitListener(queues = "updateGame")
    public void updateGame(GameDto gameDto) throws ChangeSetPersister.NotFoundException {
        if (gameDto.getId() != null || !gameDto.getId().isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }

        Game game = new Game(
                gameDto.getId(),
                gameDto.getName(),
                gameDto.getGenre(),
                gameDto.getPlatform()
        );
        gameService.updateGame(game);
    }

    @RabbitListener(queues = "deleteGame")
    public void deleteGame(String id) {
        if (id != null && !id.isEmpty()) {
            gameService.deleteGame(id);
        }
    }

}
