package com.codesquad.baseball.team14.dao;

import com.codesquad.baseball.team14.domain.game.Innings;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InningsDAO {
    private JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public InningsDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Innings> findAllById(long id) {
        String sql = "select i.id, i.score, i.score_board from innings i where i.score_board =" + id + " order by score_board_key";

        List<Innings> innings = new ArrayList<>();

        jdbcTemplate.query(sql, (rs, rowNum) -> {
            innings.add(new Innings(
                    rs.getLong("id"),
                    rs.getLong("score_board"),
                    rs.getInt("score")
            ));
            return null;
        });
        return innings;
    }

    public void createInnings(Innings innings) {
        String sql = "INSERT INTO innings (score_board, score, score_board_key)" +
                "VALUES (:score_board, :score, :inning)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("score_board", innings.getScoreBoard())
                .addValue("score", innings.getScore())
                .addValue("inning", innings.getInning());
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public void updateInning22(Long scoreBoardId, int inning, int point) {
        String sql = "UPDATE innings SET score = score + " + point + " WHERE innings.score_board = " + scoreBoardId +" AND innings.score_board_key = "+ inning;
        jdbcTemplate.update(sql);
    }

    public void updateInning(Long scoreBoardId, int inning, int point) {
        String sql = "UPDATE innings SET score = score + :point WHERE score_board = :score_board_id AND score_board_key = :inning";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("point", point)
                .addValue("score_board_id", scoreBoardId)
                .addValue("inning", inning);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

}

