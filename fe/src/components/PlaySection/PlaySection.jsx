import styled from "styled-components";
import React, { useState, useEffect, useReducer, useContext } from "react";
import PlayInning from "./PlayInning.jsx";
import PlayPitch from "./PlayPitch.jsx";
import PlaySBOInfo from "./PlaySBOInfo.jsx";
import PlayField from "./PlayField.jsx";

const initialSBOState = {
  strike: 0,
  ball: 0,
  out: 0,
};

const SBOReducer = (state, action) => {
  switch (action.type) {
    case "STRIKE":
      return { ...state, strike: state.strike + 1 };
    case "BALL":
      return { ...state, ball: state.ball + 1 };
    case "OUT":
      return { ...state, out: state.out + 1 };
    case "HIT":
      return { ...state, strike: 0, ball: 0 };
    case "SB_RESET":
      return { ...state, strike: 0, ball: 0 };
    case "TOTAL_RESET":
      return { ...initialSBOState };
    default:
      throw new Error(`Unhandled action type: ${action.type}`);
  }
};

const initialBaseState = {
  firstBase: false,
  secondBase: false,
  thirdBase: false,
  homeBase: false,
  point: 0,
};

const baseReducer = (state, action) => {
  //들어올 데이터 move (언제? 4ball, hit 발생시) 공수 교대시 reset
  switch (action.type) {
    case "MOVE":
      return {
        ...state,
        firstBase: true,
        secondBase: state.firstBase,
        thirdBase: state.secondBase,
        homeBase: state.thirdBase,
      };
    case "POINT":
      return { ...state, point: state.point + 1 };
    case "RESET":
      return { ...initialBaseState };

    default:
      throw new Error(`Unhandled action type: ${action.type}`);
  }
};

const PlaySection = props => {
  const [SBOState, SBODispatch] = useReducer(SBOReducer, initialSBOState);
  const [baseState, baseDispatch] = useReducer(baseReducer, initialBaseState);
  const [points, setPoints] = useState(0);

  return (
    <PlaySectionLayout className={props.className}>
      <PlaySBOInfo SBOState={SBOState} />
      <PlayField {...{ baseState }} />
      <PlayPitch
        {...{
          SBOState,
          SBODispatch,
          baseState,
          baseDispatch,
          points,
          setPoints,
        }}
      />
      <PlayInning></PlayInning>
      <PlayBackgroundLayer>
        <PlayBlackLayer />
      </PlayBackgroundLayer>
    </PlaySectionLayout>
  );
};

const PlaySectionLayout = styled.div`
  width: 100%;
  height: 100%;
  position: absolute;
`;

const PlayBackgroundLayer = styled.div`
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  z-index: -1;

  background-image: url("https://i2.wp.com/beechumcounty.com/wp-content/uploads/2018/01/Messages-Image2796961593.jpeg?fit=1400%2C889&ssl=1");
  background-size: cover;
`;

const PlayBlackLayer = styled.div`
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;

  background-color: black;
  opacity: 70%;
`;

export default PlaySection;
