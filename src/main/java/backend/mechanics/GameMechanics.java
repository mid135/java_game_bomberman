package backend.mechanics;

/**
 * Created by mid on 08.12.14.
 */



import backend.AccountService;
import frontend.WebSocketService;
import org.json.JSONException;
import utils.TimeHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class GameMechanics {
    private static final int STEP_TIME = 5000;

    private static final int gameTime = 150 * 1000;

    private static final int speed_inc = 1;//TODO cкорость постепенно увеличивается

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

    public void changePosition(String userName,Integer delta) throws JSONException{
        GameSession myGameSession = nameToGame.get(userName);

        GameUser myUser = myGameSession.getSelf(userName);
        myUser.getPlatform().setX(myUser.getPlatform().getX()+delta);

        if (myUser.getBall().getX()==0) {
            webSocketService.notifyGameOver(myGameSession.getFirst(), true);
            webSocketService.notifyGameOver(myGameSession.getSecond(), false);
        } else if(myUser.getEnemy().getBall().getX()==500) {
            webSocketService.notifyGameOver(myGameSession.getFirst(), false);
            webSocketService.notifyGameOver(myGameSession.getSecond(), true);
        }
        webSocketService.notifyNewState(myUser,myUser.getEnemy());

    }


    public void run() {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            GameUser myUser = session.getFirst();


            myUser.getBall().setX(session.getFirst().getBall().getX()+10);

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

        webSocketService.notifyNewState(gameSession.getSelf(first),gameSession.getSelf(second));
       // webSocketService.notifyNewState(gameSession.getSelf(second),gameSession.getSelf(first));
    }
}

