package com.codesquad.baseball.team14.domain.game;

import com.codesquad.baseball.team14.domain.Team;
import com.codesquad.baseball.team14.domain.UserType;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

public class Game {

    @Id
    private Long id;

    private Team away;

    private Team home;

    private Set<ScoreBoard> scoreBoard = new HashSet<>();

    private UserType userType;

    public Game(Long id, Team home, Team away, UserType userType) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.userType = userType;
    }

    public Game(Team away, Team home, UserType userType) {
        this.away = away;
        this.home = home;
        this.userType = userType;
    }

    public Game(Long id, Team away, Team home, Set<ScoreBoard> scoreBoard, UserType userType) {
        this.id = id;
        this.away = away;
        this.home = home;
        this.scoreBoard = scoreBoard;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public Team getAway() {
        return away;
    }

    public Team getHome() {
        return home;
    }

    public UserType getUserType() {
        return userType;
    }

    public String awayTeamName() {
        return away.getTeamName();
    }

    public String homeTeamName() {
        return home.getTeamName();
    }

    public void addScoreBoard(ScoreBoard board) {
        scoreBoard.add(board);
    }

    public Set<ScoreBoard> getScoreBoard() {
        return scoreBoard;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", away=" + away +
                ", home=" + home +
                ", scoreBoard=" + scoreBoard +
                ", userType='" + userType + '\'' +
                '}';
    }

}
