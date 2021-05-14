import { useEffect } from 'react';
import styled from 'styled-components';

import HistoryCard from './HistoryCard.js';

import { useHistoryState, useHistoryDispatch } from '../../util/store/HistoryContext.js';

const HistoryList = (props) => {
  const historyState = useHistoryState();
  const historyDispatch = useHistoryDispatch();
  
  useEffect(() => {
    historyDispatch({ type: `game/init` });
    console.log("is INIT?")
  }, [])
  
  useEffect(() => {
    console.log("히스토리 추가", historyState);
  })
  
  return (
    <HistoryListLayout className={props.className}>
      <HistoryCard className={'history-card'} isCurrent={true}/>
      <HistoryCard className={'history-card'} />
      <HistoryCard className={'history-card'} />
    </HistoryListLayout>
  )
}
const HistoryListLayout = styled.section`
  width: 100%;
  overflow: scroll;
  
  display: flex;
  flex-direction: column;

  .history-card + .history-card {
    margin-top: 5%;
  }
`
export default HistoryList;