import { createContext, useReducer, useContext } from 'react';
import { INIT, STRIKE, BALL, OUT, HIT } from '../action/game.js';

const HistoryStateContext = createContext();
const HistoryDispatchContext = createContext();

/* history
{ 
  batter: {}, 
  pitchResult: [
    { value: "", status: {strike: n, ball: m} },
    { value: "", status: {strike: n, ball: m} },
    { value: "", status: {strike: n, ball: m} },
  ],
  hitResult: { value: "" } 
}
*/

const initialHistory = [];

const historyReducer = (state, action) => {
  console.log(action)
  switch(action.type) {
    case INIT: {
      const newHistory = {
        batter: {},
        pitchResult: [],
        hitResult: {}
      }
      return [...state, newHistory];
    }
    case STRIKE: {
      
      const newHistory = state[state.length-1];
      newHistory.pitchResult = newHistory.pitchResult.concat({
        value: "strike",
        status: {...action.payload} 
      });
      
      const originState = state.filter((_, i) => i !== state.length-1);
      return [...originState, newHistory];
    }
    case BALL: {
      const newHistory = state[state.length-1];
      newHistory.pitchResult = newHistory.pitchResult.concat({
        value: "ball",
        status: {...action.payload} 
      });
      const originState = state.filter((_, i) => i !== state.length-1);
      return [...originState, newHistory];
      
    }      
    case OUT: {
      const newHistory = state[state.length-1];
      newHistory.hitResult = {...action.payload}
      
      const originState = state.filter((_, i) => i !== state.length-1);
      return [...originState, newHistory];
      
    }
    case HIT: {
      const newHistory = state[state.length-1];
      newHistory.hitResult = {...action.payload}
      
      const originState = state.filter((_, i) => i !== state.length-1);
      return [...originState, newHistory];
      
    }
    default:
      console.error("error")
      break;
  }
}

const HistoryProvider = (props) => {
  const [state, dispatch] = useReducer(historyReducer, initialHistory);
  
  return (
    <HistoryStateContext.Provider value={state}>
      <HistoryDispatchContext.Provider value={dispatch}>
        {props.children}
      </HistoryDispatchContext.Provider>
    </HistoryStateContext.Provider>
  );
}

export const useHistoryState = () => {
  const context = useContext(HistoryStateContext);
  if (!context) throw new Error('can not find HistoryStateContext');
  return context;
}

export const useHistoryDispatch = () => {
  const context = useContext(HistoryDispatchContext);
  if (!context) throw new Error('can not find HistoryDispatchContext');
  return context;
}

export default HistoryProvider;