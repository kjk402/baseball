package com.codesquad.baseball.team14.service;


import com.codesquad.baseball.team14.dao.GameDAO;
import com.codesquad.baseball.team14.domain.UserType;
import com.codesquad.baseball.team14.domain.game.Game;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private GameDAO gameDAO;

    public GameService(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public Long makeHomeGame(String home, String away) {
        return gameDAO.saveGameAndScoreBoard(home, away, UserType.HOME);
    }

    public Long makeAwayGame(String home, String away) {
        return gameDAO.saveGameAndScoreBoard(home, away, UserType.AWAY);
    }

}
