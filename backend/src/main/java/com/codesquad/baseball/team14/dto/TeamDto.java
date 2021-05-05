package com.codesquad.baseball.team14.dto;

import com.codesquad.baseball.team14.domain.Player;
import com.codesquad.baseball.team14.domain.Team;

import java.util.List;
import java.util.stream.Collectors;

public class TeamDto {
    private String teamName;
    private List<String> players;

    private TeamDto(Builder builder) {
        this.teamName = builder.teamName;
        this.players = builder.players;
    }

    public static TeamDto findPalyers(Team team, List<Player> players) {
        return new Builder()
                .teamName(team.getTeamName())
                .players(playersToName(players))
                .build();
    }

    private static List<String> playersToName(List<Player> players) {
        return players.stream().map(Player::getPlayerName).collect(Collectors.toList());
    }

    public static class Builder {
        private String teamName;
        private List<String> players;

        public TeamDto build() {
            return new TeamDto(this);
        }


        public Builder teamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Builder players(List<String> players) {
            this.players = players;
            return this;
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public List<String> getPlayers() {
        return players;
    }

}
