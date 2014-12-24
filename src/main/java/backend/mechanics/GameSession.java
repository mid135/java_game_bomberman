package backend.mechanics;


import backend.AccountService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mid
 */
public class GameSession {
    private final long startTime;
    private final GameUser first;
    private final GameUser second;
    private boolean win = false;

    private Map<String, GameUser> users = new HashMap<>();

    public GameSession(AccountService pool,String user1Name, String user2Name) {
        startTime = new Date().getTime();
        Shape ball = new Shape(250,50,2,2);

        GameUser user1 = new GameUser(50,130,ball);
        user1.setUser(pool.getUsers().get(user1Name));

        GameUser user2 = new GameUser(200,20,ball);
        user2.setUser(pool.getUsers().get(user2Name));

        user2.setEnemyName(user1);
        user1.setEnemyName(user2);

        users.put(user1Name, user1);
        users.put(user2Name, user2);

        this.first = user1;
        this.second = user2;
    }

    public GameUser getEnemy(String user) {
        String enemyName = users.get(user).getEnemy().getMyName();
        return users.get(enemyName);
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public GameUser getSelf(String user) {
        return users.get(user);
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }

    public GameUser getFirst() {
        return first;
    }

    public GameUser getSecond() {
        return second;
    }

    public  boolean isFirstWin(){
        //TODO сравнить координаты шара с началом и конца поля(это по сути координаты платформ). к кому ближе шар, тот и лох.
        return win;
    }
}
