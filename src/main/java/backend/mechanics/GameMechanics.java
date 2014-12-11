package backend.mechanics;

/**
 * Created by mid on 08.12.14.
 */



import backend.AccountService;
import backend.User;
import backend.sql_base.GameUser;
import frontend.WebSocketService;
import org.json.JSONException;
import utils.TimeHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class GameMechanics {
    private static final int STEP_TIME = 100;

    private static final int gameTime = 15 * 1000;

    private WebSocketService webSocketService;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private String waiter;

    private AccountService pool;
    public GameMechanics(WebSocketService webSocketService, AccountService p) {
        this.webSocketService = webSocketService;
        this.pool=p;
    }

    public void addUser(String user) throws JSONException{
        if (waiter != null) {
            starGame(user);
            waiter = null;
        } else {
            waiter = user;
        }
    }

    public void incrementScore(String userName) throws JSONException{
        GameSession myGameSession = nameToGame.get(userName);
        GameUser myUser = myGameSession.getSelf(userName);
        myUser.incrementMyScore();
        GameUser enemyUser = myGameSession.getEnemy(userName);
        enemyUser.incrementEnemyScore();
        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
    }


    public void run() {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (session.getSessionTime() > gameTime) {
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);

            }
        }
    }

    private void starGame(String first) throws JSONException{
        String second = waiter;
        GameSession gameSession = new GameSession(pool,first, second);
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));
    }
}

