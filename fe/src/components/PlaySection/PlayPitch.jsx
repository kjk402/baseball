import styled from "styled-components";
import { useState, useEffect, useCallback, useContext } from "react";

//const pitchResultList = ["⚡️STRIKE⚡️", "💥BALL💥", "☠️OUT☠️"];

// useEffect..로 처리
// use callback 궁금한 점 usecallback 쓰려고 Playpitch 안에 함수 계속 정의해도 되는지? 지금 모든 함수에 다 useCallback썼는데 의미없어보임

const PlayPitch = ({
  SBOState,
  SBODispatch,
  baseState,
  baseDispatch,
  points,
  setPoints,
  historyDispatch
}) => {
  //useCallback 지우고.. 컴포넌트 밖으로 함수 빼자..너무 더러움
  const judge = useCallback(
    (SBOState, SBODispatch, baseState, baseDispatch, pitchResult, historyDispatch) => {
      const { strike, ball, out } = SBOState;
      
      historyDispatch({
        type: `game/${pitchResult}`.toLowerCase(), 
        payload: {strike: strike, ball: ball}
      });

      if (strike === 2 && pitchResult === "STRIKE") {
        SBODispatch({ type: "OUT" });
        SBODispatch({ type: "SB_RESET" });
      }
      if (ball === 3 && pitchResult === "BALL") {
        SBODispatch({ type: "SB_RESET" }); //4 ball -> 주자이동
        baseDispatch({ type: "MOVE" });
        updatePoints(baseState);
      }

      if (pitchResult === "HIT") {
        baseDispatch({ type: "MOVE" }); //안타 -> 주자이동
        updatePoints(baseState);
      }

      if (out === 2 && pitchResult === "OUT") {
        //공수 교대 일어나는 곳
        SBODispatch({ type: "TOTAL_RESET" }); //3 Out -> 공수 교대, 상태 리셋
        baseDispatch({ type: "RESET" }); //화면 주자 리셋
        historyDispatch({ type: `game/init` });
        //공수 교대 api 요청
      }
    }
  );

  const getRandomPitchResult = useCallback(() => {
    const SB = ["STRIKE", "BALL", "OUT", "HIT"];
    const radomNumber = Math.floor(Math.random() * SB.length);
    return SB[`${radomNumber}`];
  });

  const updateSBO = useCallback(() => {
    const pitchResult = getRandomPitchResult();
    console.log(pitchResult);
    SBODispatch({ type: pitchResult });
    //빰빰이 보내준 context 에 pitch result 넣어주기
    judge(SBOState, SBODispatch, baseState, baseDispatch, pitchResult, historyDispatch); //여기 부분 그냥 props 받아서 내려주고 judge에서 받을 때 분해하기
  });

  const updatePoints = baseState => {
    //만루 상태에서 히트나 ball4로 MOVE가 일어났을 때 점수 +1
    const { thirdBase } = baseState;
    if (thirdBase) baseDispatch({ type: "POINT" });
  };

  return (
    <PitchButtonLayout>
      {/* <Ball /> */}
      {/* <PitchResult>{pitchResult}</PitchResult> */}
      <PitchButton
        onClick={() => {
          updateSBO();
        }}
      >
        PITCH
      </PitchButton>
    </PitchButtonLayout>
  );
};

const PitchButtonLayout = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
`;
const PitchResult = styled.div`
  position: absolute;
  width: 400px;
  top: 30%;
  left: 50%;
  text-align: center;
  font-size: 3rem;
  font-weight: bold;
  color: white;
`;

const PitchButton = styled.div`
  position: absolute;
  top: 50%;
  left: 43%;
  width: 13rem;
  padding: 10px;
  border: 1px solid white;
  border-radius: 30px;
  text-align: center;
  font-size: 4rem;
  font-weight: bold;
  color: white;

  cursor: pointer;

  &:hover {
    transform: scale(1.1);
  }

  @media (max-width: 1200px) {
    width: 12rem;
    left: 41%;
    font-size: 2rem;
  }
  @media (max-width: 768px) {
    width: 8rem;
    left: 40%;
    font-size: 1rem;
  }
`;

const Ball = styled.img.attrs({
  src: `${"http://www.bellsnwhistles.com/6spia/1asp169.gif"}`,
})`
  position: absolute;
  top: 40%;
  left: 39%;
  width: 200px;
`;
export default PlayPitch;
