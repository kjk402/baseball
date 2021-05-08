package com.codesquad.baseball.team14.dto;

import com.codesquad.baseball.team14.domain.Team;

public class MatchDto {

    private Long gameId;
    private Team user;
    private Team opponent;

    public MatchDto(Long gameId, Team user, Team opponent) {
        this.gameId = gameId;
        this.user = user;
        this.opponent = opponent;
    }

    public Long getGameId() {
        return gameId;
    }

    public Team getUser() {
        return user;
    }

    public Team getOpponent() {
        return opponent;
    }

}
