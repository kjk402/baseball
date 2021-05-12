package com.codesquad.baseball.team14.dto;

public class CurrentPlayerDto {
    private String teamName;
    private String opponentPitcher;
    private String currentHitter;
    private CurrentPlayerRecord currentPlayerRecord;

    public CurrentPlayerDto(String teamName, String opponentPitcher, String currentHitter, CurrentPlayerRecord currentPlayerRecord) {
        this.teamName = teamName;
        this.opponentPitcher = opponentPitcher;
        this.currentHitter = currentHitter;
        this.currentPlayerRecord = currentPlayerRecord;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getOpponentPitcher() {
        return opponentPitcher;
    }

    public String getCurrentHitter() {
        return currentHitter;
    }

    public CurrentPlayerRecord getBatHitDto() {
        return currentPlayerRecord;
    }
}
