package ru.vladuss.gamesgateway.services.impl;

import com.example.gameproto.GameServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vladuss.gamesgateway.dtos.GameDto;
import ru.vladuss.gamesgateway.services.GameGrpcService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameGrpcServiceImpl implements GameGrpcService {

    private GameServiceGrpc.GameServiceBlockingStub gameServiceStub;

    @Autowired
    public GameGrpcServiceImpl(GameServiceGrpc.GameServiceBlockingStub gameServiceStub) {
        this.gameServiceStub = gameServiceStub;
    }

    @Override
    public List<GameDto> getAll() {
        com.example.gameproto.GameListResponse response = gameServiceStub.getAllGames(com.example.gameproto.EmptyRequest.getDefaultInstance());
        return response.getGamesList().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GameDto getGameById(String id) {
        com.example.gameproto.GetGameRequest request = com.example.gameproto.GetGameRequest.newBuilder().setId(id).build();
        com.example.gameproto.GameResponse response = gameServiceStub.getGameById(request);

        if (response.getId().isEmpty()) {
            return null;
        }
        return mapToDto(response);
    }

    public GameDto mapToDto(com.example.gameproto.GameResponse gameResponse) {
        return new GameDto(
                gameResponse.getId(),
                gameResponse.getName(),
                gameResponse.getGenre(),
                gameResponse.getPlatform()
        );
    }
}
