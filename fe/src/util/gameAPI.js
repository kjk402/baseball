const API_URL = "http://52.78.158.138:8080";

export const requestGETCurrentGame = async () => {
  const currentGame = await fetch(`${API_URL}/games`).then(res => res.json());

  return currentGame[0];
};

export const requestGETgamePoint = async gameId => {
  const currentGame = await fetch(
    `${API_URL}/games/${gameId}/points`
  ).then(res => res.json());
  return currentGame;
};

//이닝 생성
export const requestPOSTInning = async (gameId, inning, point, teamName) => {
  let response = await fetch(`${API_URL}/games/${gameId}/points`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "API-Key": "secret",
    },
    body: JSON.stringify({
      inning: inning, //
      point: point,
      teamName: teamName,
    }),
  });
  console.log(response);
};

const makeRequestQuery = params => {
  let query = Object.keys(params)
    .map(k => encodeURIComponent(k) + "=" + encodeURIComponent(params[k]))
    .join("&");

  return `${query}`;
};

export const requestPATCHrecord = (type, playerName) => {
  const query = `?hit%26out=${type}`;

  fetch(`${API_URL}/record/${playerName}/${query}`, { method: "PATCH" })
    .then(data => data.text())
    .then(text => {
      console.log("request succeeded with JSON response", text);
    })
    .catch(function (error) {});
};
//이닝 점수 추가
export const requestPATCHInningPoint = async (
  gameId,
  inning,
  point,
  teamName
) => {
  let response = await fetch(`${API_URL}/games/${gameId}/points`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
      "API-Key": "secret",
    },
    body: JSON.stringify({
      inning: inning,
      point: point,
      teamName: teamName,
    }),
  });
  console.log(response);
};
