import { createContext, useReducer } from 'react';
import { STRIKE, BALL, OUT, HIT } from '../action/game.js';

const initialGameState = {
  histories: [],
  currentBatter: undefined,
}

/* history
{ 
  batter: {}, 
  pitchResult: [
    { value: "", status: {strike: n, ball: m} },
    { value: "", status: {strike: n, ball: m} },
    { value: "", status: {strike: n, ball: m} },
  ]
}
*/

const gameContextReducer = (state, action) => {
  let newHistories;
  switch(action.type) {
    case STRIKE:
      newHistories = [...state.histories].splice(state.length-1, 1, action.payload);
      return {...state, histories: newHistories};
    case BALL:
      newHistories = [...state.histories].splice(state.length-1, 1, action.payload);
      return {...state, histories: newHistories};
    case OUT:
      break;
    case HIT:
      break;
    default:
      console.log("error")
      break;
  }
}

const GameStateContext = createContext();
const GameDispatchContext = createContext();

const GameProvider = (props) => {
  const [state, dispatch] = useReducer(gameContextReducer, initialGameState);

  return (
    <GameStateContext.Provider value={state}>
      <GameDispatchContext value={dispatch}>
        {props.children}
      </GameDispatchContext>
    </GameStateContext.Provider>
  )
}

export default GameProvider;