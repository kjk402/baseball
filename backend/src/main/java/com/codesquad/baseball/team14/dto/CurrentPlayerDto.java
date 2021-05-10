package com.codesquad.baseball.team14.dto;

public class CurrentPlayerDto {
    private String teamName;
    private String opponentPitcher;
    private String currentHitter;

    public CurrentPlayerDto(String teamName, String opponentPitcher, String currentHitter) {
        this.teamName = teamName;
        this.opponentPitcher = opponentPitcher;
        this.currentHitter = currentHitter;
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

}
