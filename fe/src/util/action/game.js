//type
export const STRIKE = "game/strike";
export const BALL = "game/ball";
export const OUT = "game/out";
export const HIT = "game/hit";
export const INIT = "game/init";

//Fn
//Inning
export const getInning = () => {
  return localStorage.getItem("INNING");
};

export const setInitialInning = () => {
  localStorage.setItem("INNING", 1);
};

export const updateInning = () => {
  const inning = Number(getInning());
  localStorage.setItem("INNING", inning + 1);
};

export const resetInning = () => {
  localStorage.removeItem("INNING");
};

//Pitch Cnt

export const getPitchCnt = () => {
  return localStorage.getItem("PITCH_CNT");
};

export const setInitialPitchCnt = () => {
  localStorage.setItem("PITCH_CNT", 1);
};

export const updatePitchCnt = () => {
  const pitchCnt = Number(getPitchCnt());
  localStorage.setItem("PITCH_CNT", pitchCnt + 1);
};

export const resetPitchCnt = () => {
  localStorage.removeItem("PITCH_CNT");
};

export const getTurn = () => {
  return localStorage.getItem("TURN");
};

export const setInitialTurn = isDefense => {
  localStorage.setItem("TURN", isDefense);
};
export const changeTurn = isDefense => {
  const turn = Boolean(getTurn());
  turn
    ? localStorage.setItem("TURN", false)
    : localStorage.setItem("TURN", true);
};

export const resetTurn = () => {
  localStorage.removeItem("TURN");
};
