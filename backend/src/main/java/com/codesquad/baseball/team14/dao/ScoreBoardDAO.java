package com.codesquad.baseball.team14.dao;

import com.codesquad.baseball.team14.domain.game.Innings;
import com.codesquad.baseball.team14.domain.game.ScoreBoard;
import com.codesquad.baseball.team14.dto.CurrentPlayerRecord;
import com.codesquad.baseball.team14.dto.CurrentPlayerDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScoreBoardDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private InningsDAO inningsDAO;
    private PlayerDAO playerDAO;
    private RecordDAO recordDAO;

    public ScoreBoardDAO(DataSource dataSource, InningsDAO inningsDAO, PlayerDAO playerDAO,RecordDAO recordDAO) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.inningsDAO = inningsDAO;
        this.playerDAO = playerDAO;
        this.recordDAO =recordDAO;
    }

    public void createScoreBoard(ScoreBoard scoreBoard, String opponentPitcher, String currentHitter) {
        Long gameId = scoreBoard.getGameId();
        String teamName = scoreBoard.getTeam();
        String sql = "INSERT INTO score_board (game, team, opponent_pitcher, current_hitter)" +
                "VALUES (:game, :team, :opponent_pitcher, :current_hitter)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("game", gameId)
                .addValue("team", teamName)
                .addValue("opponent_pitcher", opponentPitcher)
                .addValue("current_hitter", currentHitter);
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

    public CurrentPlayerDto findByTeamName(String teamName) {
        String sql = "SELECT s.opponent_pitcher, s.current_hitter, s.team FROM score_board s where s.team = '" + teamName + "'";
        List<CurrentPlayerDto> currentPlayerDtos = new ArrayList<>();
        String playerName = findCurrentPlayer(teamName);

        jdbcTemplate.query(sql, ((rs, rowNum) -> {
                    CurrentPlayerRecord currentPlayerRecord = recordDAO.findByPlayerName(playerName);
                            currentPlayerDtos.add(new CurrentPlayerDto(
                            rs.getString("team"),
                            rs.getString("opponent_pitcher"),
                            rs.getString("current_hitter"),
                                    currentPlayerRecord)
                    );
                return null;
                }));
        return currentPlayerDtos.get(0);
    }

    /*
    public Long findScoreBoardId(String teamName) {
        Long scoreBoardId;
        String sql = "SELECT s.id FROM score_board s WHERE s.team = '" + teamName + "'";
        scoreBoardId = this.jdbcTemplate.queryForObject(sql, Long.class);
        return scoreBoardId;
    }
     */
    public String findCurrentPlayer(String teamName) {
        String playerName;
        String sql = "SELECT s.opponent_pitcher FROM score_board s where s.team = '" + teamName + "'";
        playerName = (String) jdbcTemplate.queryForObject(
                sql, String.class);
        return playerName;
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

    public void updatePlayer(String playerName) {
        String teamName = playerDAO.getTeamNameByPlayerName(playerName);
        List<String> players = new ArrayList<>(playerDAO.getPlayerNames(teamName));
        int currentIndex = players.indexOf(playerName);
        int nextIndex;
        if (currentIndex > 7) {
            nextIndex = 0;
        } else {
            nextIndex = currentIndex + 1;
        }
        String nextPlayer = players.get(nextIndex);
        String sql = "UPDATE baseball.score_board SET score_board.current_hitter = '" + nextPlayer + "'WHERE score_board.team = '" + teamName + "'";
        jdbcTemplate.update(sql);
    }

    public Long findScoreBoardId(String teamName) {
        Long scoreBoardId;
        String sql = "SELECT s.id FROM score_board s WHERE s.team = '" + teamName + "'";
        scoreBoardId = this.jdbcTemplate.queryForObject(sql, Long.class);
        return scoreBoardId;
    }

}
