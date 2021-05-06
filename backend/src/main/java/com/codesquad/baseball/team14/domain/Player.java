package com.codesquad.baseball.team14.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;

public class Player {

    @Id
    private Long id;

    private String playerName;
    private String team;
    private Record record;


    public Player(String playerName, Record record) {
        this.playerName = playerName;
        this.record = record;
    }

    public Long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getTeam() {
        return team;
    }

    public Record getRecord() {
        return record;
    }

}
