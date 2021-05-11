package com.codesquad.baseball.team14.dao;


import com.codesquad.baseball.team14.domain.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerDAO {
    private JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PlayerDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<String> getPlayerNames(String teamName) {
        List<String> players = new ArrayList<>();
        String sql = "SELECT player.* FROM baseball.player WHERE player.team = '" + teamName + "'";
        jdbcTemplate.query(sql, (rs, rowNum) -> players.add(rs.getString("player_name")
        ));
        return players;
    }

    public String getTeamNameByPlayerName(String playerName) {
        String sql = "SELECT player.team FROM baseball.player WHERE player.player_name = '" + playerName + "'";
        String teamName = (String) jdbcTemplate.queryForObject(
                sql, String.class);
        return teamName;
    }

}

