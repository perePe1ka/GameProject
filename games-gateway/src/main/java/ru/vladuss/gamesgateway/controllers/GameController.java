package ru.vladuss.gamesgateway.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vladuss.gamesgateway.dtos.GameDto;
import ru.vladuss.gamesgateway.services.impl.GameServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    private final GameServiceImpl gameService;

    public GameController(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<GameDto>> getAllGames() {
        List<GameDto> games = gameService.getAll();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable String id) {
        GameDto game = gameService.getById(id);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

    @PostMapping
    public ResponseEntity<String> createGame(@RequestBody GameDto gameDto) {
        gameService.createGame(gameDto);
        return ResponseEntity.accepted().body("Создание записи о игре(асинхрон)...");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGame(@PathVariable String id, @RequestBody GameDto gameDto) {
        gameDto.setId(id);
        gameService.updateGame(gameDto);
        return ResponseEntity.accepted().body("Изменение записи о игре(асинхрон)...");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.accepted().body("Удаление записи о игре(асинхрон)...");
    }
}
