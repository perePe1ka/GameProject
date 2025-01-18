package ru.vladuss.gamesservice.services;

import ru.vladuss.gamesservice.entityes.Game;

import java.util.List;

public interface GameService {
    Game createGame(Game game);

    List<Game> getAll();

    Game getById(String id);

    Game updateGame(Game game);

    void deleteGame(String id);
}
