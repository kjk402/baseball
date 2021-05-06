package com.codesquad.baseball.team14.dao;

import com.codesquad.baseball.team14.domain.Player;
import com.codesquad.baseball.team14.domain.Record;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PlayerDao {
    private JdbcTemplate template;

    public PlayerDao(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public List<Player> findByTeamName(String teamName) {
        // todo :  선수 순서 보장
        String sql = "SELECT p.player_name as pid, p.type, p.name, r.id as r.out, r.hits, r.at_bat, r.average " +
                "FROM player p " +
                "inner JOIN record r ON r.player_name = p.player_name " +
                "WHERE p.team = '" + teamName + "' order by p.player_name;";

        List<Player> players = new ArrayList<>();

        template.query(sql, (rs, rowNum) -> {
            Record record = new Record(
                    rs.getInt("at_bat"),
                    rs.getInt("hits"),
                    rs.getInt("outs"),
                    rs.getDouble("average")
            );
            players.add(new Player(
                    rs.getString("name"),
                    record
            ));
            return null;
        });

        return players;
    }

}
