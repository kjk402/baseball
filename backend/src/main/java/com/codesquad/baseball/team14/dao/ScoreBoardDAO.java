package com.codesquad.baseball.team14.dao;

import com.codesquad.baseball.team14.domain.game.Innings;
import com.codesquad.baseball.team14.domain.game.ScoreBoard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScoreBoardDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private InningsDAO inningsDAO;

    public ScoreBoardDAO(DataSource dataSource, InningsDAO inningsDAO) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.inningsDAO = inningsDAO;
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

    public List<ScoreBoard> findAllScoreBoard() {
        String sql = "SELECT id, game, team from score_board";
        List<ScoreBoard> scoreBoards = new ArrayList<>();
        jdbcTemplate.query(sql, (rs, rowNum) ->
                scoreBoards.add(
                        new ScoreBoard(
                                rs.getLong("id"),
                                rs.getLong("game"),
                                rs.getString("team")
                        )));
        return scoreBoards;
    }

    public ScoreBoard findByGameIdAndTeamName(Long id, String teamName) {
        String sql = "select s.id, s.game,s.team from score_board s where s.game = " + id + " and s.team = '" + teamName + "'";
        return findScoreBoard(sql);
    }

    public List<ScoreBoard> findByGameId(Long id) {
        String sql = "select s.id, s.game,s.team from score_board s where s.game = " + id;
        return findScoreBoards(sql);
    }

    private ScoreBoard findScoreBoard(String sql) {

        List<ScoreBoard> query = jdbcTemplate.query(sql, (rs, rowNum) -> {
            List<Innings> innings = inningsDAO.findAllById(rs.getLong("id"));

            ScoreBoard board = new ScoreBoard(
                    rs.getLong("id"),
                    rs.getLong("game"),
                    rs.getString("team"),
                    innings
            );

            return board;
        });
        return query.get(0);
    }

    private List<ScoreBoard> findScoreBoards(String sql) {
        List<ScoreBoard> scoreBoards = new ArrayList<>();
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            List<Innings> innings = inningsDAO.findAllById(rs.getLong("id"));

            scoreBoards.add(new ScoreBoard(
                    rs.getLong("id"),
                    rs.getLong("game"),
                    rs.getString("team"),
                    innings
            ));
            return null;
        });
        return scoreBoards;
    }

}
