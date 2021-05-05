package com.codesquad.baseball.team14.service;

import com.codesquad.baseball.team14.domain.Player;
import com.codesquad.baseball.team14.domain.PlayerRepository;
import com.codesquad.baseball.team14.domain.Team;
import com.codesquad.baseball.team14.domain.TeamRepository;
import com.codesquad.baseball.team14.dto.TeamDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<Team> getList() {
        return teamRepository.findAllTeams();
    }

    public TeamDto getPlayers(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        List<Player> players = playerRepository.findPlayersByTeam(teamName);
        return TeamDto.findPalyers(team, players);
    }

}
