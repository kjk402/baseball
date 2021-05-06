package com.codesquad.baseball.team14.dto;

import com.codesquad.baseball.team14.domain.Player;
import com.codesquad.baseball.team14.domain.Record;
import com.codesquad.baseball.team14.domain.Team;

import java.util.*;
import java.util.stream.Collectors;

public class TeamDto {
    private String teamName;
    private LinkedHashMap<String,Record> recordList;

    private TeamDto(Builder builder) {
        this.teamName = builder.teamName;
        this.recordList = builder.recordList;
    }

    public static TeamDto findPalyers(Team team, LinkedHashMap<String, Record> recordList) {
        return new Builder()
                .teamName(team.getTeamName())
                .recordList(recordList)
                .build();
    }

    private static List<String> playersToName(List<Player> players) {
        return players.stream().map(Player::getPlayerName).collect(Collectors.toList());
    }

    public static class Builder {
        private String teamName;

        private LinkedHashMap<String,Record> recordList = new LinkedHashMap<>();

        public TeamDto build() {
            return new TeamDto(this);
        }

        public Builder teamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Builder recordList(LinkedHashMap<String,Record> recordList){
            this.recordList = recordList;
            return this;
        }

    }

    public String getTeamName() {
        return teamName;
    }

    public LinkedHashMap<String, Record> getRecordList() {
        return recordList;
    }
}
