package ru.vladuss.gamesgateway.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vladuss.gamesgateway.dtos.GameDto;
import ru.vladuss.gamesgateway.services.GameService;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameGrpcServiceImpl gameServiceGrpc;
    private final GameProducerImpl gameProducer;

    public GameServiceImpl(GameGrpcServiceImpl gameServiceGrpc, GameProducerImpl gameProducer) {
        this.gameServiceGrpc = gameServiceGrpc;
        this.gameProducer = gameProducer;
    }

    @Cacheable(value = "gamesCache")
    @Override
    public List<GameDto> getAll() {
        LOGGER.info("Fetching all games from gRPC service");
        return gameServiceGrpc.getAll();
    }

    @Cacheable(value = "gameCache", key = "#id")
    @Override
    public GameDto getById(String id) {
        LOGGER.info("Fetching game with id = {} from gRPC service", id);
        return gameServiceGrpc.getGameById(id);
    }

    @CacheEvict(value = "gamesCache", allEntries = true)
    @Override
    public void createGame(GameDto gameDto) {
        LOGGER.info("Sending create game request for game with id = {}", gameDto.getId());
        gameProducer.sendGameCreated(gameDto);
    }

    @CacheEvict(value = {"gameCache", "gamesCache"}, key = "#gameDto.id", allEntries = true)
    @Override
    public void updateGame(GameDto gameDto) {
        LOGGER.info("Sending update game request for game with id = {}", gameDto.getId());
        gameProducer.sendGameUpdated(gameDto);
    }

    @CacheEvict(value = {"gameCache", "gamesCache"}, key = "#id", allEntries = true)
    @Override
    public void deleteGame(String id) {
        LOGGER.info("Sending delete game request for game with id = {}", id);
        gameProducer.sendGameDelete(id);
    }
}
