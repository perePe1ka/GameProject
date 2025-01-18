package ru.vladuss.gamesservice.services.impl;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.vladuss.gamesservice.entityes.Game;
import ru.vladuss.gamesservice.services.GameService;

import java.util.List;

@GrpcService
public class GameGrpcServiceImpl extends com.example.gameproto.GameServiceGrpc.GameServiceImplBase {

    private final GameService gameService;


    public GameGrpcServiceImpl(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void getGameById(com.example.gameproto.GetGameRequest request, StreamObserver<com.example.gameproto.GameResponse> response) {
        String gameId = request.getId();
        Game game = gameService.getById(gameId);

        com.example.gameproto.GameResponse gameResponse;
        if (game != null) {
            gameResponse = com.example.gameproto.GameResponse.newBuilder()
                    .setId(game.getId())
                    .setName(game.getName())
                    .setGenre(game.getGenre())
                    .setPlatform(game.getPlatform())
                    .build();
        } else {
            gameResponse = com.example.gameproto.GameResponse.newBuilder()
                    .setId(" ")
                    .setName(" ")
                    .setGenre(" ")
                    .setPlatform(" ")
                    .build();
        }

        response.onNext(gameResponse);
        response.onCompleted();
    }

    @Override
    public void getAllGames(com.example.gameproto.EmptyRequest request, StreamObserver<com.example.gameproto.GameListResponse> response) {
        List<Game> games = gameService.getAll();

        com.example.gameproto.GameListResponse.Builder builder = com.example.gameproto.GameListResponse.newBuilder();
        for (Game game : games) {
            com.example.gameproto.GameResponse gameResponse = com.example.gameproto.GameResponse.newBuilder()
                    .setId(game.getId())
                    .setName(game.getName())
                    .setGenre(game.getGenre())
                    .setPlatform(game.getPlatform())
                    .build();
            builder.addGames(gameResponse);
        }

        response.onNext(builder.build());
        response.onCompleted();
    }
}
