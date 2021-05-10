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
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private ScoreBoardDAO scoreBoardDAO;
    private PlayerDAO playerDAO;

    public GameDAO(DataSource dataSource, ScoreBoardDAO scoreBoardDAO, PlayerDAO playerDAO) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.scoreBoardDAO = scoreBoardDAO;
        this.playerDAO = playerDAO;
    }

    public Long saveGameAndScoreBoard(String home, String away, UserType userType) {
        Long gameId = createGame(home, away, userType);
        ScoreBoard homeBoard = new ScoreBoard(gameId, home);
        ScoreBoard awayBoard = new ScoreBoard(gameId, away);
        List<String> homePlayers = new ArrayList<>(playerDAO.getPlayerNames(home));
        List<String> awayPlayers = new ArrayList<>(playerDAO.getPlayerNames(away));
        createScoreBoard(gameId, homeBoard, awayPlayers.get(8), homePlayers.get(0));
        createScoreBoard(gameId, awayBoard, homePlayers.get(8), awayPlayers.get(0));
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

    public void createScoreBoard(Long gameId, ScoreBoard scoreBoard, String opponentPitcher, String currentHitter) {
        scoreBoardDAO.createScoreBoard(scoreBoard, opponentPitcher, currentHitter);
    }

    public String findUserTeamNameByGameId(Long id) {
        String sql = "select if(g.user_type = 'home',g.home,g.away) as user_team_name from game g where g.id = " + id;
        List<String> query = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("user_team_name"));
        return query.get(0);
    }

    public void deleteGame(Long gameId) {
        String sql = "DELETE g, s FROM game AS g INNER JOIN score_board AS s ON g.id = s.game where g.id = " + gameId;
        jdbcTemplate.update(sql);
    }

}


