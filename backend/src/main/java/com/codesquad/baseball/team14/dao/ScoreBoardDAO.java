package com.codesquad.baseball.team14.dao;

import com.codesquad.baseball.team14.domain.game.ScoreBoard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ScoreBoardDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ScoreBoardDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void createScoreBoard(ScoreBoard scoreBoard) {
        Long gameId = scoreBoard.getGameId();
        String teamName = scoreBoard.getTeam();
        String sql = "INSERT INTO score_board (game, team)" +
                "VALUES (:game, :team)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("game", gameId)
                .addValue("team", teamName);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }


}

