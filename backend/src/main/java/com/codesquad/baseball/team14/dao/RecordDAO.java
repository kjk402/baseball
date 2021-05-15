package com.codesquad.baseball.team14.dao;


import com.codesquad.baseball.team14.dto.CurrentPlayerRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecordDAO {

    private JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RecordDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public CurrentPlayerRecord findByPlayerName(String playerName) {
        String sql = "SELECT r.at_bat, r.hits from record r where r.player_name= '" + playerName + "'";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("playerName", playerName);
        List<CurrentPlayerRecord> currentPlayerRecords = new ArrayList<>();
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            currentPlayerRecords.add(new CurrentPlayerRecord(
                    rs.getInt("at_bat"),
                    rs.getInt("hits")
            ));
            return null;
        });
        return currentPlayerRecords.get(0);
    }

}
