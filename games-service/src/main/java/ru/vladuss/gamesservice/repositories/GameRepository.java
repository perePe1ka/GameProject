package ru.vladuss.gamesservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.vladuss.gamesservice.entityes.Game;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
}
