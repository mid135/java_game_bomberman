package backend;

import backend.Mechanics.GameWebSocket;
import backend.Mechanics.UserGameState;

/**
 * Created by mid on 12.11.14.
 */
public interface WebSocketService {
    void notifyStartGame(UserGameState userGameState);
    void notifyGameOver(UserGameState userGameState);
    void notifyUpdateGameState(UserGameState userGameState);
    void addSocket(GameWebSocket gameWebSocket);
    void removeSocket(GameWebSocket gameWebSocket);
}
