package backend.Mechanics;

import backend.WebSocketService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mid on 12.11.14.
 */
public class WebSocketServiceImpl implements WebSocketService{
    private final Map<String, GameWebSocket> loginToSocket = new ConcurrentHashMap<>();
    @Override
    public void notifyStartGame(UserGameState userGameState) {
        loginToSocket.get(userGameState.getMyGameUser().getLogin()).startGame(userGameState);
    }
    @Override
    public void notifyGameOver(UserGameState userGameState) {
        loginToSocket.get(userGameState.getMyGameUser().getLogin()).gameOver(userGameState);
    }
    @Override
    public void notifyUpdateGameState(UserGameState userGameState) {
        loginToSocket.get(userGameState.getMyGameUser().getLogin()).updateGameState(userGameState);
    }
    @Override
    public void addSocket(GameWebSocket gameWebSocket) {
        loginToSocket.put(gameWebSocket.getMyLogin(), gameWebSocket);
    }
    @Override
    public void removeSocket(GameWebSocket gameWebSocket) {
        loginToSocket.remove(gameWebSocket.getMyLogin());
    }
}
