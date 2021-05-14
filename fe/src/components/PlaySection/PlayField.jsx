import styled from "styled-components";
import PlayFieldCanvas from "./PlayFieldCanvas.jsx";
const IMAGE = {
  RUNNING:
    "https://media.tenor.com/images/6d02cd24ab50932ca62ce555d74e384c/tenor.gif",
  STANDING:
    "https://i.pinimg.com/originals/f7/5f/f2/f75ff23cd22d200f24bfd21f3a8b1f86.gif",
};
//hit 상태에 따라서 running 렌더를 해주자
//달리는건 2초뒤에 사라지게
//이미지 바꿔치기로 리팩토링
//종료 시점에 서있게 하고 -> 이미지 바꾸기 = 애니메이션이 종료될때 이벤트 실행 -> 상태를 바꿈(이미지 주소를 바꿈->리렌더링)
const renderPlayer = baseState => {
  const { firstBase, secondBase, thirdBase, homeBase } = baseState;
  //서있는 선수
  if (homeBase)
    return (
      <>
        <PlayerOnFirstBase />
        <PlayerOnSecondBase />
        <PlayerOnThirdBase />
        <RunningThirdToHome />
        <RunningSecondToThird />
        <RunningFirstToSecond />
        <RunningHomeToFirst />
      </>
    );
  if (thirdBase)
    return (
      <>
        <PlayerOnFirstBase />
        <PlayerOnSecondBase />
        <PlayerOnThirdBase />
        <RunningSecondToThird />
        <RunningFirstToSecond />
        <RunningHomeToFirst />
      </>
    );
  if (secondBase)
    return (
      <>
        <RunningFirstToSecond />
        <RunningHomeToFirst />
        <PlayerOnFirstBase />
        <PlayerOnSecondBase />
      </>
    );
  if (firstBase) {
    return (
      <>
        <RunningHomeToFirst />
        <PlayerOnFirstBase />
      </>
    );
  }
};

const PlayField = ({ baseState }) => {
  return (
    baseState && (
      <>
        <PlayFieldCanvas></PlayFieldCanvas>
        {renderPlayer(baseState)}
      </>
    )
  );
};

const StandingPlayer = styled.img.attrs({
  src: `${IMAGE.STANDING}`,
})`
  animation-delay: 3s;
  width: 6rem;
  @media (max-width: 1200px) {
    width: 5rem;
  }
  @media (max-width: 768px) {
    width: 4rem;
  }
`;

const RunningPlayer = styled.img.attrs({
  src: `${IMAGE.RUNNING}`,
})`
  opacity: 0%;
  animation-iteration-count: 1;
  animation-duration: 2s;
`;

const RunningHomeToFirst = styled(RunningPlayer)`
  width: 7rem;
  position: absolute;
  bottom: 10%;
  left: 48%;
  animation-name: homeToFirst;

  @keyframes homeToFirst {
    from {
      bottom: 10%;
      left: 48%;
      opacity: 100;
    }
    to {
      bottom: 48%;
      left: 86%;
      opacity: 0;
    }
  }
  @media (max-width: 1200px) {
    width: 5rem;
  }
  @media (max-width: 768px) {
    width: 4rem;
  }
`;
const RunningFirstToSecond = styled(RunningPlayer)`
  width: 7rem;
  position: absolute;
  transform: rotateY(180deg);
  animation-name: firstToSecond;

  @keyframes firstToSecond {
    from {
      top: 40%;
      right: 9%;
      opacity: 100;
    }
    to {
      top: 17%;
      right: 48%;
      opacity: 0;
    }
  }
  @media (max-width: 1200px) {
    width: 5rem;
  }
  @media (max-width: 768px) {
    width: 4rem;
  }
`;

const RunningSecondToThird = styled(RunningPlayer)`
  width: 7rem;
  position: absolute;
  transform: rotateY(180deg);
  animation-name: secondToThird;
  animation-duration: 3s;

  @keyframes secondToThird {
    from {
      top: 17%;
      left: 48%;
      opacity: 100;
    }
    to {
      top: 43%;
      left: 9%;
      opacity: 0;
    }
  }
  @media (max-width: 1200px) {
    width: 5rem;
  }
  @media (max-width: 768px) {
    width: 4rem;
  }
`;

const RunningThirdToHome = styled(RunningPlayer)`
  width: 7rem;
  position: absolute;
  animation-name: thirdToHome;
  animation-duration: 3s;

  @keyframes thirdToHome {
    from {
      top: 43%;
      left: 9%;
      opacity: 100;
    }
    to {
      top: 80%;
      left: 48%;
      opacity: 0;
    }
  }
  @media (max-width: 1200px) {
    width: 5rem;
  }
  @media (max-width: 768px) {
    width: 4rem;
  }
`;

const PlayerOnFirstBase = styled(StandingPlayer)`
  position: absolute;
  top: 40%;
  right: 9%;

  @media (max-width: 1200px) {
    top: 39%;
    right: 8%;
  }
  @media (max-width: 768px) {
    top: 45%;
    right: 8%;
  }
`;

const PlayerOnSecondBase = styled(StandingPlayer)`
  position: absolute;
  top: 17%;
  right: 48%;

  @media (max-width: 1200px) {
    top: 16%;
    right: 47%;
  }
  @media (max-width: 768px) {
    top: 18%;
    right: 47%;
  }
`;

const PlayerOnThirdBase = styled(StandingPlayer)`
  position: absolute;
  top: 43%;
  left: 9%;

  @media (max-width: 1200px) {
    top: 39%;
    left: 8%;
  }
  @media (max-width: 768px) {
    top: 45%;
    left: 8%;
  }
`;
export default PlayField;
