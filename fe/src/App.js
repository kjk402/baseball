import { Route } from 'react-router-dom';

import HomePage from "./pages/HomePage.jsx";
import MainPage from "./pages/MainPage.jsx";

const App = () => {
  return (
    <>
      <Route path="/" exact component={HomePage} />
      <Route path="/main" component={MainPage} />
    </>
  );
}

export default App;
