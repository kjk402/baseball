import { useState, useEffect } from "react";
import styled from "styled-components";

import GameCard from "./GameCard";

import API from "../../util/API.js"

const GameList = () => {

  const [teams, setTeams] = useState([]);
  
  useEffect(() => {
    
    // can not use directly
    const fetchGames = () => {
      const response = API.get.games();
      return response;
    }
    const fetchTeams = async () => {
      let teamsInGame = [];
      
      const games = await fetchGames();
      games.forEach( ({home, away}) => teamsInGame.push(home, away));
      
      const createCombination = (target, remains) => {
        
        return remains.map((remain) => {
          const teamTarget = {...target, info: null };
          const teamRemain = {...remain, info: null };
          if (teamsInGame.includes(target.teamName)) { teamTarget.info = "IN_GAME" }
          if (teamsInGame.includes(remain.teamName)) { teamRemain.info = "IN_GAME" }
          return [teamTarget, teamRemain];
        });
      };
      
      const response = await API.get.teams();
      let teamsCombination = [];
      response.forEach((teamObj) => {
        const remainsArr = response.filter((select) => select.teamName !== teamObj.teamName);
        teamsCombination.push(createCombination(teamObj, remainsArr));
      });
      // console.log('teamsCombination', teamsCombination);
      setTeams([...teamsCombination.flat(1)]);
    }
    fetchTeams();
  }, []);

  const handleClickTeam = async (team) => {
    
    const gamesAwayResult = await API.post.gamesAway(team);
    console.log("gamesAwayResult", gamesAwayResult)
    
    if (gamesAwayResult.type === "EXIST") {
      localStorage.setItem("currentPlayTeam", JSON.stringify(gamesAwayResult.result));
      // 진행중인 게임 알리기
    } else if (gamesAwayResult.type === "PLAY") {
      localStorage.setItem("currentPlayTeam", JSON.stringify(gamesAwayResult.result));
    }
    
    // 임시
    window.location.href="/main";
    
  }
  return (
    <GameListLayout>
      {teams.map((team, idx) => (
        <GameCard key={`gameCard-${idx}`} game={team} idx={idx + 1} onClick={handleClickTeam}/>
      ))}
    </GameListLayout>
  );
};

const GameListLayout = styled.div`
  width: 60%;
  height: 33vh;
  display: flex;
  flex-direction: column;
  margin-top: 5%;
  overflow: hidden;

  &:hover {
    overflow-y: scroll;
  }
`;
export default GameList;
