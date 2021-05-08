package com.codesquad.baseball.team14.controller;


import com.codesquad.baseball.team14.domain.Team;
import com.codesquad.baseball.team14.domain.game.Game;
import com.codesquad.baseball.team14.dto.MatchDto;
import com.codesquad.baseball.team14.service.GameService;
import com.codesquad.baseball.team14.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Baseball API"}, description = "Baseball API made by Bat")
@RestController
@RequestMapping("/games")
public class GameController {

    private TeamService teamService;
    private GameService gameService;

    public GameController(TeamService teamService, GameService gameService) {
        this.teamService = teamService;
        this.gameService = gameService;
    }

    @PostMapping("/home")
    @ApiOperation(value = "팀 선택", notes = "팀 선택, 게임 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public MatchDto homeTeamMatch(@RequestParam(value = "home",required=false) String home,
                                  @RequestParam(value = "away",required=false) String away) {

        Long gameId = gameService.makeHomeGame(home, away);
        Team homeTeam = teamService.findTeam(home);
        Team awayTeam = teamService.findTeam(away);
        return new MatchDto(gameId, homeTeam, awayTeam);

    }

    @PostMapping("/away")
    @ApiOperation(value = "팀 선택", notes = "팀 선택, 게임 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public MatchDto awayTeamMatch(@RequestParam(value = "home",required=false) String home,
                                  @RequestParam(value = "away",required=false) String away) {

        Long gameId = gameService.makeAwayGame(home, away);
        Team homeTeam = teamService.findTeam(home);
        Team awayTeam = teamService.findTeam(away);
        return new MatchDto(gameId, awayTeam, homeTeam);

    }

}
