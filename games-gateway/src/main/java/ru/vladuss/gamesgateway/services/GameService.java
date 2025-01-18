package ru.vladuss.gamesgateway.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ru.vladuss.gamesgateway.dtos.GameDto;

import java.util.List;

public interface GameService {
    @Cacheable(value = "gamesCache")
    List<GameDto> getAll();

    @Cacheable(value = "gameCache", key = "#id")
    GameDto getById(String id);

    @CacheEvict(value = "gamesCache", allEntries = true)
    void createGame(GameDto gameDto);

    @CacheEvict(value = {"gameCache", "gamesCache"}, key = "#gameDto.id", allEntries = true)
    void updateGame(GameDto gameDto);

    @CacheEvict(value = {"gameCache", "gamesCache"}, key = "#id", allEntries = true)
    void deleteGame(String id);
}
