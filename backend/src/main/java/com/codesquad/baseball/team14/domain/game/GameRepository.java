package com.codesquad.baseball.team14.domain.game;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends CrudRepository<Game, Long> {
    @Query("SELECT * from game WHERE game.id = :gameId")
    Game findByGameId(@Param("gameId") Long gameId);
}

