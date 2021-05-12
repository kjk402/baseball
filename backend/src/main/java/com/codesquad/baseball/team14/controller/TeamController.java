package com.codesquad.baseball.team14.controller;


import com.codesquad.baseball.team14.domain.Team;
import com.codesquad.baseball.team14.dto.TeamDto;
import com.codesquad.baseball.team14.service.RecordService;
import com.codesquad.baseball.team14.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"Baseball API"}, description = "Baseball API made by Bat")
@RestController
public class TeamController {
    private final TeamService teamService;
    private final RecordService recordService;

    public TeamController(TeamService teamService, RecordService recordService) {
        this.teamService = teamService;
        this.recordService = recordService;
    }

    @GetMapping("/teams")
    @ApiOperation(value = "팀 불러오기", notes = "팀들 반환합니다.")
    public ResponseEntity<List<Team>> getTeams() {
        List<Team> teams = teamService.getList();
        return ResponseEntity.ok().body(teams);
    }

    @GetMapping("/players/{teamName}")
    @ApiOperation(value = "플레이어 이름, 기록", notes = "팀별로 플레이어의 이름과 기록을 반환합니다.")
    public ResponseEntity<TeamDto> getPlayersAndRecord(@ApiParam("팀 식별자") @PathVariable("teamName") String teamName) {
        TeamDto teamDto = teamService.getPlayers(teamName);
        return ResponseEntity.ok().body(teamDto);
    }

}
