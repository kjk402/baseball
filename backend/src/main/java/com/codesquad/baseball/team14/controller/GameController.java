package com.codesquad.baseball.team14.controller;


import com.codesquad.baseball.team14.domain.Team;
import com.codesquad.baseball.team14.domain.game.ScoreBoard;
import com.codesquad.baseball.team14.dto.*;
import com.codesquad.baseball.team14.service.GameService;
import com.codesquad.baseball.team14.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation(value = "홈팀 선택", notes = "유저가 홈팀으로 게임 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public Object homeTeamMatch(@RequestParam(value = "home", required = false) String home,
                                @RequestParam(value = "away", required = false) String away) {
        if (gameService.checkExists(home, away)) {
            return ResponseEntity.badRequest().body("이미 선택된 게임입니다.");
        }
        Long gameId = gameService.makeHomeGame(home, away);
        Team homeTeam = teamService.findTeam(home);
        Team awayTeam = teamService.findTeam(away);
        return new MatchDto(gameId, homeTeam, awayTeam);
    }

    @PostMapping("/away")
    @ApiOperation(value = "어웨이팀 선택", notes = "유저가 홈팀으로 게임 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public Object awayTeamMatch(@RequestParam(value = "home", required = false) String home,
                                @RequestParam(value = "away", required = false) String away) {

        if (gameService.checkExists(home, away)) {
            return ResponseEntity.badRequest().body("이미 선택된 게임입니다.");
        }
        Long gameId = gameService.makeAwayGame(home, away);
        Team homeTeam = teamService.findTeam(home);
        Team awayTeam = teamService.findTeam(away);
        return new MatchDto(gameId, awayTeam, homeTeam);
    }

    @PostMapping("/{gameId}/points")
    @ApiOperation(value = "점수 추가합니다.", notes = "유저가 홈팀으로 게임 생성합니다.")
    @ResponseStatus(HttpStatus.OK)
    public void plusPoint(@ApiParam("게임 식별자") @PathVariable Long gameId, @RequestBody PointDto pointDto) {
        ScoreBoard scoreBoard = gameService.findScoreBoardByTeamName(gameId, pointDto);
        gameService.plusPoint(scoreBoard, pointDto);
    }

    @GetMapping("/{gameId}/points")
    @ApiOperation(value = "스코어보드 보기", notes = "해당 게임의 스코어 보드를 가져옵니다.")
    @ResponseStatus(HttpStatus.OK)
    public GameScoreDto getGameBoard(@ApiParam("게임 식별자") @PathVariable Long gameId) {
        List<ScoreBoard> scoreBoard = gameService.findScoreBoardByGameId(gameId);
        String userTeamName = gameService.findUserTeamNameByGameId(gameId);

        ScoreDto teamScoreDTO = gameService.scoreToTeamScoreDTO(scoreBoard.get(0));
        ScoreDto teamScoreDTO1 = gameService.scoreToTeamScoreDTO(scoreBoard.get(1));

        if (teamScoreDTO.getTeamName().equals(userTeamName)) {
            return new GameScoreDto(teamScoreDTO, teamScoreDTO1);
        }
        return new GameScoreDto(teamScoreDTO1, teamScoreDTO);
    }

    @DeleteMapping("/{gameId}")
    @ApiOperation(value = "게임 삭제", notes = "게임 데이터 삭제합니다.")
    public String deleteGame(@ApiParam("게임 식별자") @PathVariable Long gameId) {
        gameService.deleteGame(gameId);
        return gameId + "삭제했습니다.";
    }

    @GetMapping("/{teamName}/currentPlayer")
    @ApiOperation(value = "현재 팀", notes = "상태팀 투수와 현재 타석 선수반환합니다.")
    @ResponseStatus(HttpStatus.OK)
    public CurrentPlayerDto getCurrentPlayer(@ApiParam("팀 식별자") @PathVariable String teamName) {
        CurrentPlayerDto currentPlayerDto = gameService.getCurrent(teamName);
        return currentPlayerDto;
    }

}
