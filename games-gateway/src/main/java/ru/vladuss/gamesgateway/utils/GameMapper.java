package ru.vladuss.gamesgateway.utils;

import com.example.gameproto.GameResponse;
import org.springframework.stereotype.Component;
import ru.vladuss.gamesgateway.dtos.GameDto;

@Component
public class GameMapper {
    public static GameResponse toGameResponse(GameDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Game не может быть NULL");
        }

        return GameResponse.newBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setGenre(dto.getGenre())
                .setPlatform(dto.getPlatform())
                .build();
    }


    public static GameDto toGameDto(GameResponse response) {

        if (response == null) {
            throw new IllegalArgumentException("GameResponse не может быть NULL");
        }

        GameDto dto = new GameDto();
        dto.setId(response.getId());
        dto.setName(response.getName());
        dto.setGenre(response.getGenre());
        dto.setPlatform(response.getPlatform());
        return dto;
    }
}