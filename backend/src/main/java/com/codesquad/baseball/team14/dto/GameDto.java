package com.codesquad.baseball.team14.dto;

public class GameDto {
    private Long gameId;

    private String userType;
    private String home;
    private String away;

    public GameDto(Long gameId, String userType, String home, String away) {
        this.gameId = gameId;
        this.userType = userType;
        this.home = home;
        this.away = away;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getUserType() {
        return userType;
    }

    public String getHome() {
        return home;
    }

    public String getAway() {
        return away;
    }

}
