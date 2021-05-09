package com.codesquad.baseball.team14.domain.game;

import org.springframework.data.annotation.Id;

import java.util.List;

public class ScoreBoard {
    @Id
    private Long id;

    private Long game;
    private String team;
    private List<Innings> innings;


    public ScoreBoard(Long id, Long game, String teamName, List<Innings> innings) {
        this.id = id;
        this.game = game;
        this.team = teamName;
        this.innings = innings;
    }

    public ScoreBoard(Long id, Long gameId, String team) {
        this.id = id;
        this.game = gameId;
        this.team = team;
    }

    public ScoreBoard(Long gameId, String team) {
        this.game = gameId;
        this.team = team;
    }

    public Innings updateScore(int round, int point) {
        return new Innings(this.id, point, round);
    }

    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return game;
    }

    public String teamName() {
        return team;
    }

    public String getTeam() {
        return team;
    }

    public List<Innings> getInnings() {
        return innings;
    }

}
