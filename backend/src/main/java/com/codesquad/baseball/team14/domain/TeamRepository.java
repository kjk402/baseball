package com.codesquad.baseball.team14.domain;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, String> {
    @Query("SELECT * from baseball.team WHERE team.team_name = :teamName")
    Team findByTeamName(@Param("teamName") String teamName);

    @Query("SELECT team_name from baseball.team ORDER BY team.id")
    List<Team> findAllTeams();
}
