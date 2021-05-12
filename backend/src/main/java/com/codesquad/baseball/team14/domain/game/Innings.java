package com.codesquad.baseball.team14.domain.game;

import org.springframework.data.annotation.Id;

public class Innings {
    @Id
    private Long id;

    private int score;
    private Long scoreBoard;
    private int inning;

    public Innings(Long scoreBoard, int score, int inning) {
        this.scoreBoard = scoreBoard;
        this.score = score;
        this.inning = inning;
    }

    public Innings(Long id, Long scoreBoard, int score) {
        this.id = id;
        this.scoreBoard = scoreBoard;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public Long getScoreBoard() {
        return scoreBoard;
    }

    public int getScore() {
        return score;
    }

    public int getInning() {
        return inning;
    }

    public void update(int point) {
        this.score += point;
    }

}
