package ru.vladuss.gamesservice.services.impl;

import org.springframework.stereotype.Service;
import ru.vladuss.gamesservice.entityes.Game;
import ru.vladuss.gamesservice.repositories.GameRepository;
import ru.vladuss.gamesservice.services.GameService;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;


    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game getById(String id) {
        return gameRepository.findById(id).orElse(null);
    }

    @Override
    public Game updateGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void deleteGame(String id) {
        gameRepository.deleteById(id);
    }
}
