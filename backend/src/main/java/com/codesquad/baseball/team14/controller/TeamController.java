package com.codesquad.baseball.team14.controller;


import com.codesquad.baseball.team14.domain.Team;
import com.codesquad.baseball.team14.dto.TeamDto;
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

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    @ApiOperation(value = "팀 불러오기", notes = "팀들 반환합니다.")
    public ResponseEntity<List<Team>> getTeams() {
        List<Team> teams = teamService.getList();
        return ResponseEntity.ok().body(teams);
    }

    @GetMapping("/players/{teamName}")
    @ApiOperation(value = "플레이어 이름", notes = "팀별로 플레이어의 이름을 반환합니다.")
    public ResponseEntity<TeamDto> getDetail(@ApiParam("팀 식별자") @PathVariable("teamName") String detailHash) {
        TeamDto teamDto = teamService.getPlayers(detailHash);
        return ResponseEntity.ok().body(teamDto);
    }

}
