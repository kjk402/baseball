package com.codesquad.baseball.team14.dto;

import java.util.List;

public class ScoreDto {
    private String teamName;
    private List<Integer> point;

    public ScoreDto(String teamName, List<Integer> point) {
        this.teamName = teamName;
        this.point = point;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Integer> getPoint() {
        return point;
    }
}
