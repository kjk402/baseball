package com.codesquad.baseball.team14.domain;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, String> {
    @Query("SELECT player.* from baseball.player INNER JOIN baseball.team ON player.team = team.team_name WHERE team = :teamName;")
    List<Player> findPlayersByTeam(@Param("teamName") String teamName);
}

