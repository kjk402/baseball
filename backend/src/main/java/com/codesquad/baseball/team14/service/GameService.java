package com.codesquad.baseball.team14.service;


import com.codesquad.baseball.team14.dao.GameDAO;
import com.codesquad.baseball.team14.dao.InningsDAO;
import com.codesquad.baseball.team14.dao.ScoreBoardDAO;
import com.codesquad.baseball.team14.domain.UserType;
import com.codesquad.baseball.team14.domain.game.Innings;
import com.codesquad.baseball.team14.domain.game.ScoreBoard;
import com.codesquad.baseball.team14.dto.PointDto;
import com.codesquad.baseball.team14.dto.ScoreDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private GameDAO gameDAO;
    private ScoreBoardDAO scoreBoardDAO;
    private InningsDAO inningsDAO;

    public GameService(GameDAO gameDAO, ScoreBoardDAO scoreBoardDAO, InningsDAO inningsDAO) {
        this.gameDAO = gameDAO;
        this.scoreBoardDAO = scoreBoardDAO;
        this.inningsDAO = inningsDAO;
    }

    public Long makeHomeGame(String home, String away) {
        return gameDAO.saveGameAndScoreBoard(home, away, UserType.HOME);
    }

    public Long makeAwayGame(String home, String away) {
        return gameDAO.saveGameAndScoreBoard(home, away, UserType.AWAY);
    }

    public boolean checkExists(String home, String away) {
        List<ScoreBoard> scoreBoards = scoreBoardDAO.findAllScoreBoard();
        for (ScoreBoard scoreBoard : scoreBoards) {
            if (scoreBoard.getTeam().equals(home) || scoreBoard.getTeam().equals(away)) {
                return true;
            }
        }
        return false;
    }

    public void plusPoint(ScoreBoard scoreBoard, PointDto pointDto) {
        Innings innings = scoreBoard.updateScore(pointDto.getInning(), pointDto.getPoint());
        inningsDAO.createInnings(innings);
    }

    public ScoreBoard findScoreBoardByTeamName(Long gameId, PointDto pointDto) {
        return scoreBoardDAO.findByGameIdAndTeamName(gameId, pointDto.getTeamName());
    }

    public List<ScoreBoard> findScoreBoardByGameId(Long gameId) {
        return scoreBoardDAO.findByGameId(gameId);
    }

    public String findUserTeamNameByGameId(Long gameId) {
        return gameDAO.findUserTeamNameByGameId(gameId);
    }

    public static ScoreDto scoreToTeamScoreDTO(ScoreBoard board) {
        List<Integer> scores = board.getInnings().stream()
                .map(Innings::getScore)
                .collect(Collectors.toList());
        return new ScoreDto(board.teamName(), scores);
    }

}
