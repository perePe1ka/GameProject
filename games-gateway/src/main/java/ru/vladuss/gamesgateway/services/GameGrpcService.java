package ru.vladuss.gamesgateway.services;

import ru.vladuss.gamesgateway.dtos.GameDto;

import java.util.List;

public interface GameGrpcService {
    List<GameDto> getAll();

    GameDto getGameById(String id);
}
