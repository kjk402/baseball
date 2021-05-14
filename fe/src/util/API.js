// 이거는 제가 데이지의 방식으로 따라가는 것으로.
const END_POINT = "http://52.78.158.138:8080"
const API = {
  get: {
    teams: async () => {
      const response = await fetch(`${END_POINT}/teams`);
      return await response.json();
    },
    games: async () => {
      const response = await fetch(`${END_POINT}/games`);
      return await response.json();
    },
    gamePoints: async (gameId) => {
      const response = await fetch(`${END_POINT}/games/${gameId}/points`);
      return await response.json();
    },
    gameCurrentPlayer: async (teamName) => {
      const response = await fetch(`${END_POINT}/games/${teamName}/currentPlayer`);
      return await response.json();
    },
  },
  post: {
    gamesAway: async (payload) => {
      let selectedTeam;
      const parse = Object.entries(payload).map(([key, value]) => {
        selectedTeam = value;
        return `${key}=${value}`;
      });
      try {
        const response = await fetch(`${END_POINT}/games/away?${parse.join("&")}`, {
          method: "POST",
          headers: { "Content-Type": "application/json" }
        });
        
        // 에러 로직 처리 임시
        // if (response.status >= 400) throw new Error({teamName: parse[0].split("=")[0], msg: response.text()} );
        if (response.status >= 400) {
          const games = await API.get.games();
          
          return {type: "EXIST", result: games.find((game) => game.away === selectedTeam || game.home === selectedTeam) }
          // return {type: "EXIST", result: await API.get.gamesCurrentPlayer(parse[0].split("=")[1]) }
        } else {
          const responseBody = await response.json();
          const result = {
            gameId: responseBody.gameId, 
            userType: responseBody.userType,
            home:responseBody.opponent.teamName, 
            away: responseBody.user.teamName
          }
          return {type: "PLAY", result }
        }
      } catch (e) {
        console.error("e", e);
        return e;
      }
    }
  },
};

export default API;
