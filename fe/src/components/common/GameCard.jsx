import styled, { css } from "styled-components";

const GameCard = ({ game, idx, onClick: handleTeamClick }) => {
  const [home, away] = game;
  
  return (
    <GameCardLayout>
      <GameCardRow>
        <GameNumber>GAME {idx}</GameNumber>
      </GameCardRow>

      <GameCardRow>
        <TeamName teamInfo={home.info} onClick={ () => { handleTeamClick({away: home.teamName, home: away.teamName }) } }>
          {home.teamName}
        </TeamName>
        <Versus>vs</Versus>
        <TeamName teamInfo={away.info} onClick={ () => { handleTeamClick({away: away.teamName, home: home.teamName }) } }>
          {away.teamName}
        </TeamName>
      </GameCardRow>
    </GameCardLayout>
  );
};

const GameCardLayout = styled.div`
  border: 1px solid black;
  background-color: white;
  border-radius: 20px;
  text-align: center;
  cursor: pointer;

  & + & {
    margin-top: 10px;
  }

  &:hover {
    background-color: #fff99e;
  }
`;

const GameCardRow = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 10px 0px;
`;
const GameNumber = styled.div`
  width: 100%;
  font-size: 1.5rem;
  color: red;
`;

const Versus = styled.span`
  width: 100%;
  color: #5f5d5d;
  font-size: 2rem;
  padding: 30px 0px;
`;
const TeamName = styled.span`
  width: 100%;
  font-size: 3rem;
  padding: 20px 0px;
  color: ${({teamInfo}) => teamInfo ? "grey": "black" };
  &:hover {
    ${ ({teamInfo}) => {
      if (teamInfo) {
        return css`&::after {
          content: "이미 선택된 팀입니다.";
          color: red;
          font-size: 2rem;
          
          position: absolute;
          margin-top: 25px;
          /* padding:10px; */
          /* margin-left: -20px; */
        }`;
      } else {
        return css`color: "red"`
      }
    }};
  }
  
`;

export default GameCard;
