package frontend;

import backend.User;
import backend.sql_base.GameUser;
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

    public void notifyMyNewScore(GameUser user) throws JSONException{
        userSockets.get(user.getMyName()).setMyScore(user);
    }

    public void notifyEnemyNewScore(GameUser user) throws JSONException{
        userSockets.get(user.getMyName()).setEnemyScore(user);
    }

    public void notifyStartGame(GameUser user) throws JSONException{
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.startGame(user);
    }

    public void notifyGameOver(GameUser user, boolean win) {
        userSockets.get(user.getMyName()).gameOver(user, win);
    }
}
