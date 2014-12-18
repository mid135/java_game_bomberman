package frontend;

import backend.mechanics.GameUser;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mid
 */
public class WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyName(), user);
    }

    public void notifyNewState(GameUser user1,GameUser user2) throws JSONException{
        userSockets.get(user1.getMyName()).setState(user1);
        userSockets.get(user2.getMyName()).setState(user2);
    }

    public void notifyStartGame(GameUser user) throws JSONException{
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.startGame(user);
    }

    public void notifyGameOver(GameUser user, boolean win) {
        userSockets.get(user.getMyName()).gameOver(win);
    }
}
