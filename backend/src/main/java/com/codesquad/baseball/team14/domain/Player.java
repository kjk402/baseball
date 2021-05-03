package com.codesquad.baseball.team14.domain;

import org.springframework.data.annotation.Id;

public class Player {

    @Id
    private Long id;

    private String playerName;
    private Double average;

    private String team;


    public Player(String playerName, String team) {
        this.playerName = playerName;
        this.team = team;
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

}
