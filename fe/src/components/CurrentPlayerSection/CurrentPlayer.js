import {useEffect, useState} from 'react';
import styled from 'styled-components';

import API from '../../util/API.js';

// {
//   "teamName": "SSG 랜더스",
//   "opponentPitcher": "유희관",
//   "currentHitter": "최정",
//   "currentPlayerRecord": {
//     "atBat": 2,
//     "hits": 0
//   }
// }

// {
//   "teamName": "두산 베어스",
//   "opponentPitcher": "김광현",
//   "currentHitter": "오재원",
//   "currentPlayerRecord": {
//     "atBat": 2,
//     "hits": 1
//   }
// }

const CurrentPlayer = (props) => {
  const [currentPlayerInfo, setCurrentPlayerInfo] = useState({});
  
  useEffect(() => {
    const isAwayTurn = localStorage.getItem("TURN");
    const currentPlayTeam = JSON.parse(localStorage.getItem("currentPlayTeam"));
  
    const fetchCurrentPlayer = async () => {
      
      const away = currentPlayTeam.away;
      const home = currentPlayTeam.home;
      let response;
      if (isAwayTurn) {
        response = await API.get.gameCurrentPlayer(away);  
      } else {
        response = await API.get.gameCurrentPlayer(home);
      }
      setCurrentPlayerInfo(response);
    }
    fetchCurrentPlayer();
    console.log(currentPlayerInfo)
  }, []);

  if (Object.keys(currentPlayerInfo).length === 0) return <></>;

  return (
    
    <CurrentPlayerLayout className={props.className}>
      <CurrentPlayerRow>
        <CurrentPlayerPosition> 투수 </CurrentPlayerPosition>
        <CurrentPlayerStatus>
          <CurrentPlayerName>{currentPlayerInfo.opponentPitcher}</CurrentPlayerName>
          {/* <CurrentPlayerDetails>#39</CurrentPlayerDetails> */}
        </CurrentPlayerStatus>
      </CurrentPlayerRow>
      <CurrentPlayerRow>
        <CurrentPlayerPosition> 타자 </CurrentPlayerPosition>
        <CurrentPlayerStatus>
          <CurrentPlayerName>{currentPlayerInfo.currentHitter}</CurrentPlayerName>
          <CurrentPlayerDetails>
            {`${currentPlayerInfo.currentPlayerRecord.atBat}타석 ${currentPlayerInfo.currentPlayerRecord.hits}안타`}
          </CurrentPlayerDetails>
        </CurrentPlayerStatus>
      </CurrentPlayerRow>
    </CurrentPlayerLayout>
  )
}

const CurrentPlayerLayout = styled.section`
  width: 100%;
  
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const CurrentPlayerRow = styled.div`
  width: 100%;
  padding: 0 5%;
  
  display: flex;
  flex-direction: column;

  & + & {
    padding-top: 5%;
  }
`
const CurrentPlayerPosition = styled.span`
  font-size: 4rem;
  font-weight: 500;
  color:white;
  
  /* ::after {
    content: ${props => props.value};
    content: "투수"
  } 왜 안되지? */ 
`

const CurrentPlayerStatus = styled.div`
  font-size: 3rem;
  font-weight: 600;
  
  display: flex;
  
  span + span {
    padding-left: 3%;
  }
`
const CurrentPlayerName = styled.span`
  color: rgb(189, 228, 235);
`
const CurrentPlayerDetails = styled.span`
  color: rgb(103, 166, 192);
`

export default CurrentPlayer;