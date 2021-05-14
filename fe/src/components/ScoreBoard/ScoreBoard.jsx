import { useState, useEffect } from "react";
import styled from "styled-components";

import API from '../../util/API.js';

// const result = {
//   gameId: responseBody.gameId, 
//   userType: responseBody.userType,
//   home:responseBody.opponent.teamName, 
//   away: responseBody.user.teamName
// }
const ScoreBoard = (props) => {
  const [boardInfo, setBoardInfo] = useState({});
  
  useEffect(() => {
    const currentPlayTeam = JSON.parse(localStorage.getItem("currentPlayTeam"));
    const fetchGamePoints = async () => {
      const response = await API.get.gamePoints(currentPlayTeam.gameId);
      const away = {
        teamName: response.user.teamName,
        point: response.user.point.reduce((acc, current) => acc + current )
      }
      const home = {
        teamName: response.opponent.teamName,
        point: response.opponent.point.reduce((acc, current) => acc + current, 0)
      }
    
      setBoardInfo({home, away});
    }
    fetchGamePoints();

  }, [])
  
  if (Object.keys(boardInfo).length === 0) return <></>;
  
  return (
    <ScoreBoardLayout className={props.className}>
      <ScoreBoardRow>
        <ScoreBoardTitle>BASEBALL GAME ONLINE</ScoreBoardTitle>
      </ScoreBoardRow>
      <ScoreBoardRow>
        <ScoreBoardMatch>
          <ScoreBoardTeam>{boardInfo.home.teamName}</ScoreBoardTeam>
          <ScoreBoardPoint>{boardInfo.home.point}</ScoreBoardPoint>
          <ScoreBoardVersus />
          <ScoreBoardPoint>{boardInfo.away.point}</ScoreBoardPoint>
          <ScoreBoardTeam>{boardInfo.away.teamName}</ScoreBoardTeam>
        </ScoreBoardMatch>
      </ScoreBoardRow>
    </ScoreBoardLayout>
  );
};

const ScoreBoardLayout = styled.section`
  width: 100%;

  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const ScoreBoardRow = styled.div`
  width: 100%;

  display: flex;
  justify-content: center;
  align-items: center;

  & + & {
    padding-top: 5%;
  }
`;

const ScoreBoardTitle = styled.span`
  font-size: 4rem;
  font-weight: bold;
  color: white;
`;

const ScoreBoardMatch = styled.div`
  width: 100%;
  padding: 0 10%;
  box-sizing: border-box;

  display: flex;
  align-items: baseline;
  justify-content: space-evenly;
  font-weight: bold;
`;

const ScoreBoardTeam = styled.span`
  font-size: 5.5rem;
  color: white;
`;
const ScoreBoardPoint = styled.span`
  font-size: 5.5rem;
  color: white;
`;
const ScoreBoardVersus = styled.span`
  font-size: 4rem;
  color: grey;
  ::after {
    content: "VS";
  }
`;
export default ScoreBoard;
