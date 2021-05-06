package com.codesquad.baseball.team14.service;

import com.codesquad.baseball.team14.domain.*;
import com.codesquad.baseball.team14.dto.TeamDto;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;


@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final RecordService recordService;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository, RecordService recordService) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.recordService = recordService;
    }

    public List<Team> getList() {
        return teamRepository.findAllTeams();
    }

    public TeamDto getPlayers(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        List<Player> players = playerRepository.findPlayersByTeam(teamName);
        LinkedHashMap<String,List<Record>> recordList = recordService.getRecordByPlayer(players);
        return TeamDto.findPalyers(team, recordList);
    }

}
