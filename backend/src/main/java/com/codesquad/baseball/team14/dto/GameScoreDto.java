package com.codesquad.baseball.team14.dto;

public class GameScoreDto {
    private ScoreDto user;
    private ScoreDto opponent;

    public GameScoreDto(ScoreDto user, ScoreDto opponent) {
        this.user = user;
        this.opponent = opponent;
    }

    public ScoreDto getUser() {
        return user;
    }

    public ScoreDto getOpponent() {
        return opponent;
    }

}
