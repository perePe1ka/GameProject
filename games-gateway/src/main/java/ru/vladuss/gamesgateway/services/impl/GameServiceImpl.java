package ru.vladuss.gamesgateway.services.impl;

import io.lettuce.core.RedisConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;
import ru.vladuss.gamesgateway.dtos.GameDto;
import ru.vladuss.gamesgateway.services.GameService;
import ru.vladuss.gamesgateway.utils.GameMapper;
import ru.vladuss.gamesgateway.utils.GameProducerImpl;

import java.util.List;

@Service
@EnableCaching
public class GameServiceImpl implements GameService {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameGrpcServiceImpl gameServiceGrpc;
    private final GameProducerImpl gameProducer;
    private final CacheManager cacheManager;

    public GameServiceImpl(GameGrpcServiceImpl gameServiceGrpc,
                           GameProducerImpl gameProducer,
                           CacheManager cacheManager) {
        this.gameServiceGrpc = gameServiceGrpc;
        this.gameProducer = gameProducer;
        this.cacheManager = cacheManager;
    }

    @Override
    public List<GameDto> getAll() {
        if (!isRedisAvailable()) {
            LOGGER.warn("Redis недоступен, берём из базы");
            return gameServiceGrpc.getAll();
        }

        var cache = cacheManager.getCache("gamesCache");
        if (cache != null) {
            var cachedValue = cache.get("all");
            if (cachedValue != null) {
                LOGGER.info("Берём данные из кэша");
                return (List<GameDto>) cachedValue.get();
            }
        }
        LOGGER.info("Берём данные из базы");
        List<GameDto> games = gameServiceGrpc.getAll();
        if (cache != null) {
            cache.put("all", games);
        }
        return games;
    }

    @Override
    public GameDto getById(String id) {
        if (!isRedisAvailable()) {
            LOGGER.warn("Redis недоступен. Берём данные с айди = {} по грпс из бд.", id);
            return gameServiceGrpc.getGameById(id);
        }

        var cache = cacheManager.getCache("gameCache");
        if (cache != null) {
            var cachedValue = cache.get(id);
            if (cachedValue != null) {
                LOGGER.info("Берём данные с айди = {} из кэша", id);
                return (GameDto) cachedValue.get();
            }
        }
        LOGGER.info("Берём данные с айди = {} по грпс", id);
        GameDto game = gameServiceGrpc.getGameById(id);
        if (cache != null) {
            cache.put(id, game);
        }
        return game;
    }

    @Override
    public void createGame(GameDto gameDto) {
        LOGGER.info("Отрпавили запрос на создание с айди = {}", gameDto.getId());
        gameProducer.sendGameCreated(GameMapper.toGameResponse(gameDto));
        clearCaches();
    }

    @Override
    public void updateGame(GameDto gameDto) {
        LOGGER.info("Отрпавили запрос на изменение с айди = {}", gameDto.getId());
        gameProducer.sendGameUpdated(GameMapper.toGameResponse(gameDto));
        clearCaches();
    }

    @Override
    public void deleteGame(String id) {
        LOGGER.info("Отрпавили запрос на удаление с айди = {}", id);
        gameProducer.sendGameDelete(id);
        clearCaches();
    }

    private void clearCaches() {
        LOGGER.info("Кэш почищен");
        var gamesCache = cacheManager.getCache("gamesCache");
        if (gamesCache != null) {
            gamesCache.clear();
        }
        var gameCache = cacheManager.getCache("gameCache");
        if (gameCache != null) {
            gameCache.clear();
        }
    }

    boolean isRedisAvailable() {
        try (var connect = redisConnectionFactory.getConnection()) {
            connect.ping();
            return true;
        } catch (RedisConnectionException e) {
            LOGGER.warn("Redis упал: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            LOGGER.warn("Redis упал: {}", e.getMessage());
            return false;
        }
    }

}
