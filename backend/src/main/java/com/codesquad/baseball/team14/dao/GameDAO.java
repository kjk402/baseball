package com.codesquad.baseball.team14.dao;

import com.codesquad.baseball.team14.domain.UserType;
import com.codesquad.baseball.team14.domain.game.ScoreBoard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GameDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private ScoreBoardDAO scoreBoardDAO;

    public GameDAO(DataSource dataSource, ScoreBoardDAO scoreBoardDAO) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.scoreBoardDAO = scoreBoardDAO;
    }

    public Long saveGameAndScoreBoard(String home, String away, UserType userType) {
        Long gameId = createGame(home, away, userType);
        ScoreBoard homeBoard = new ScoreBoard(gameId, home);
        ScoreBoard awayBoard = new ScoreBoard(gameId, away);
        createScoreBoard(gameId, homeBoard);
        createScoreBoard(gameId, awayBoard);
        return gameId;
    }

    public Long createGame(String home, String away, UserType userType) {
        String sql = "INSERT INTO game (home, away, user_type)" +
                "VALUES (:home, :away, :user_type)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("home", home)
                .addValue("away", away)
                .addValue("user_type", userType.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder, new String[]{"ID"});
        return keyHolder.getKey().longValue();
    }

    public void createScoreBoard(Long gameId, ScoreBoard scoreBoard) {
        scoreBoardDAO.createScoreBoard(scoreBoard);
    }

    public String findUserTeamNameByGameId(Long id) {
        String sql = "select if(g.user_type = 'home',g.home,g.away) as user_team_name from game g where g.id = " + id;
        List<String> query = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("user_team_name"));
        return query.get(0);
    }

}
