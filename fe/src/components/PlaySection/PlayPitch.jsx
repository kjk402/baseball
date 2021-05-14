import styled from "styled-components";
import { useState, useEffect, useCallback, useContext } from "react";
import {
  requestPATCHrecord,
  requestPATCHInningPoint,
  requestPOSTInning,
} from "../../util/gameAPI.js";
import {
  getPitchCnt,
  setInitialPitchCnt,
  updatePitchCnt,
  resetPitchCnt,
  getInning,
} from "../../util/action/game.js";

//이닝 바꾸기, 공수 교체

const PlayPitch = ({
  SBOState,
  SBODispatch,
  baseState,
  baseDispatch,
  historyDispatch,
  inningPoint,
  setInningPoint,
  setIsDefense,
}) => {
  const [currentPitch, setCurrentPitch] = useState("");
  const { secondBase, thirdBase } = baseState;
  useEffect(() => {
    resetPitchCnt();
  }, []);

  const updateHistory = pitchResult => {
    historyDispatch({ type: `game/${pitchResult}` });
  };

  const judge = useCallback(pitchResult => {
    const { strike, ball, out } = SBOState;

    historyDispatch({
      type: `game/${pitchResult}`.toLowerCase(),
      payload: { strike: strike, ball: ball },
    });

    if (strike === 2 && pitchResult === "STRIKE") {
      SBODispatch({ type: "OUT" });
      SBODispatch({ type: "SB_RESET" });
      if (!secondBase)
        requestPOSTInning(3, getInning(), inningPoint, "두산 베어스"); //🔥3 부분에 게임 아이디 넣어주기
    }

    if (ball === 3 && pitchResult === "BALL") {
      SBODispatch({ type: "SB_RESET" });
      updatePoints(baseState);
      if (!secondBase)
        requestPOSTInning(3, getInning(), inningPoint, "두산 베어스"); //🔥3 부분에 게임 아이디 넣어주기
      if (thirdBase) setInningPoint(x => x + 1);
    }

    if (pitchResult === "HIT") {
      baseDispatch({ type: "MOVE" });
      updatePoints(baseState);
      if (!secondBase)
        requestPOSTInning(3, getInning(), inningPoint, "두산 베어스"); //🔥3 부분에 게임 아이디 넣어주기
      if (thirdBase) setInningPoint(x => x + 1);
    }

    if (out === 2 && pitchResult === "OUT") {
      //공수 교대 일어나는 곳
      requestPATCHInningPoint(3, getInning(), inningPoint, "두산 베어스"); //이닝 총점.🔥3 부분에 게임 아이디 넣어주기
      setIsDefense(x => !x); //🔥공수 교대 (공격 뱃지 보여줄 때 사용 가능)
      console.log(inningPoint);
      //----------리셋------------------
      SBODispatch({ type: "TOTAL_RESET" }); //SBO 신호 리셋
      baseDispatch({ type: "RESET" }); //화면 주자 리셋
      historyDispatch({ type: `game/init` });
      resetPitchCnt();
      setInningPoint(0);
    }
  });

  const getRandomPitchResult = useCallback(() => {
    const SB = ["STRIKE", "BALL", "OUT", "HIT"];
    const radomNumber = Math.floor(Math.random() * SB.length);
    return SB[`${radomNumber}`];
  });

  const updatePitchCount = () => {
    const LSPitchCnt = getPitchCnt();
    !LSPitchCnt ? setInitialPitchCnt() : updatePitchCnt();
  };

  const updateRecord = pitchResult => {
    if (pitchResult === "HIT") requestPATCHrecord("hit", "허경민"); //🔥"허경민" 현재 투수이름으로 넣어주기
    if (pitchResult === "OUT") requestPATCHrecord("out", "허경민"); //🔥"허경민" 현재 투수이름으로 넣어주기
  };

  const handlePitchResult = useCallback(() => {
    const pitchResult = getRandomPitchResult();
    setCurrentPitch(pitchResult);
    SBODispatch({ type: pitchResult });
    judge(pitchResult);
    updateRecord(pitchResult);
    //🔥빰빰이 보내준 context 에 pitch result 넣어주기
    //updateHistory(pitchResult); 🔥안 됨 빰빰 확인 부탁
  });

  const updatePoints = baseState => {
    if (thirdBase) baseDispatch({ type: "POINT" });
  };

  return (
    <PitchButtonLayout>
      <PitchResult>{currentPitch}</PitchResult>
      <PitchButton
        onClick={() => {
          handlePitchResult();
          updatePitchCount();
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
  top: 10%;
  right: 4%;
  text-align: center;
  font-size: 5rem;
  font-weight: bold;
  color: #24f7d3;
  @media (max-width: 1200px) {
    font-size: 3rem;
  }
  @media (max-width: 768px) {
    font-size: 1rem;
  }
`;

const PitchButton = styled.div`
  position: absolute;
  top: 50%;
  left: 45%;
  width: 10%;
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
    left: 41%;
    font-size: 3rem;
  }
  @media (max-width: 768px) {
    width: 7%;
    left: 40%;
    font-size: 1rem;
  }
`;

export default PlayPitch;
