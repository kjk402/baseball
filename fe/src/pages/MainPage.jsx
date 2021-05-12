import styled from "styled-components";

import PlaySection from "../components/PlaySection/PlaySection.jsx";
import CurrentPlayer from "../components/CurrentPlayerSection/CurrentPlayer.js";
import ScoreBoard from "../components/ScoreBoard/ScoreBoard.jsx";
import HistoryList from "../components/HistorySection/HistoryList.js";

import HistoryProvider from "../util/store/HistoryContext.js";

const MainPage = () => {
  return (
    <MainPageLayout>
      <ScoreBoard className={"grid-area__Score"} />
      <CurrentPlayer className={"grid-area__CurrentPlayer"} />
      
      <HistoryProvider>  
        <PlaySection className={"grid-area__Play"} />
        <HistoryList className={"grid-area__History"} />
      </HistoryProvider>        
    </MainPageLayout>
  );
};

const MainPageLayout = styled.div`
  display: grid;
  grid-template-rows: repeat(4, 25vh);
  grid-template-columns: repeat(4, 1fr);
  grid-template-areas:
    "Score Score Score CurrentPlayer"
    "Play Play Play History"
    "Play Play Play History"
    "Play Play Play History";

  .grid-area__Score {
    grid-area: Score;
    background-color: black;
  }
  .grid-area__CurrentPlayer {
    grid-area: CurrentPlayer;
    background-color: black;
  }
  .grid-area__Play {
    grid-area: Play;
    position: relative;
  }
  .grid-area__History {
    grid-area: History;
    background-color: black;
  }
`;
export default MainPage;
