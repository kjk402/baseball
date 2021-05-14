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
}) => {
  const [currentPitch, setCurrentPitch] = useState("");
  const { thirdBase } = baseState;
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
    }
    if (ball === 3 && pitchResult === "BALL") {
      SBODispatch({ type: "SB_RESET" }); //4 ball -> 주자이동
      baseDispatch({ type: "MOVE" });
      updatePoints(baseState);
      if (thirdBase) setInningPoint(x => x + 1);
    }

    if (pitchResult === "HIT") {
      baseDispatch({ type: "MOVE" }); //안타 -> 주자이동
      updatePoints(baseState);
      if (thirdBase) setInningPoint(x => x + 1);
    }

    if (out === 2 && pitchResult === "OUT") {
      //공수 교대 일어나는 곳
      requestPOSTInning(3, inningPoint, "두산 베어스"); //게임아이디,팀이름  서버에서 받아온걸로, 작동 x
      requestPATCHInningPoint(3, getInning(), inningPoint, "두산 베어스"); //게임아이디,팀이름  서버에서 받아온걸로, 작동x
      //isDefense 토글하는 것 추가
      SBODispatch({ type: "TOTAL_RESET" }); //3 Out -> 공수 교대, 상태 리셋
      baseDispatch({ type: "RESET" }); //화면 주자 리셋
      historyDispatch({ type: `game/init` });
      resetPitchCnt();
      setInningPoint(0);

      //공수 교대 api 요청 /games/{gameId}/points 이닝 생성 post 현재 게임, 현재 이닝 전체 공유
    }
  });

  const getRandomPitchResult = useCallback(() => {
    const SB = ["STRIKE", "BALL", "OUT", "HIT"];
    const radomNumber = Math.floor(Math.random() * SB.length);
    return SB[`${radomNumber}`];
  });

  const updatePitchCount = () => {
    const LSPitchCnt = getPitchCnt();
    if (!LSPitchCnt) {
      setInitialPitchCnt();
    } else {
      updatePitchCnt();
    }
  };

  const updateInningPoints = () => {};

  const updateRecord = pitchResult => {
    if (pitchResult === "HIT") requestPATCHrecord("hit", "허경민"); //🔥현재 투수이름으로 넣어주기
    if (pitchResult === "OUT") requestPATCHrecord("out", "허경민");
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
