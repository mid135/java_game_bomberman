package frontend;

import backend.mechanics.GameUser;
import frontend.mobile.MobileWebSocket;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mid
 */
public class WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();
    private Map<String, MobileWebSocket> mobileSockets = new HashMap<>();

    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyName(), user);
    }

    public void addMobile(MobileWebSocket mobile) {
        mobileSockets.put(mobile.getMyName(), mobile);
    }

    public void notifyNewState(GameUser user1,GameUser user2) throws JSONException{
        userSockets.get(user1.getMyName()).setState(user1);
        userSockets.get(user2.getMyName()).setState(user2);
    }

    public void notifyNewStateMobile(String user) throws JSONException {
        mobileSockets.get(user).setState();
    }
    public void notifyStartGame(GameUser user) throws JSONException{
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.startGame(user);
    }

    public void notyfyStartGameMobile(String user) throws JSONException {
        MobileWebSocket mobileSoket = mobileSockets.get(user);
        mobileSoket.startGame();
    }

    public void notifyGameOver(GameUser user) {
        userSockets.get(user.getMyName()).gameOver( user.getMyName(), user.getScore(), user.getEnemy().getMyName(), user.getEnemy().getScore());
    }
}
