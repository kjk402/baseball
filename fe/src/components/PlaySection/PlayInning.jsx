import styled from "styled-components";
import { getInning } from "../../util/action/game.js";
const PlayInning = ({ isDefense }) => {
  const renderOffenseDefense = () => {
    return isDefense ? "초 수비" : "말 공격"; //홈팀 선택 고정이라 초 수비로 고정함
  };

  return (
    <Inning>
      {getInning()}회 {renderOffenseDefense()}
    </Inning>
  );
};

const Inning = styled.div`
  position: absolute;
  top: 4%;
  right: 4%;
  color: white;
  font-size: 4rem;

  @media (max-width: 1200px) {
    font-size: 2rem;
  }
  @media (max-width: 768px) {
    font-size: 1rem;
  }
`;

export default PlayInning;
