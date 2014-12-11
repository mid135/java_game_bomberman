package backend.mechanics;


import backend.AccountService;
import backend.User;
import backend.sql_base.GameUser;

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

    private Map<String, GameUser> users = new HashMap<>();

    public GameSession(AccountService pool,String user1Name, String user2Name) {
        startTime = new Date().getTime();
        GameUser user1 = new GameUser();
        user1.setUser(pool.getUsers().get(user1Name));
        user1.setEnemyName(user2Name);

        GameUser user2 = new GameUser();
        user2.setUser(pool.getUsers().get(user2Name));
        user2.setEnemyName(user1Name);

        users.put(user1Name, user1);
        users.put(user2Name, user2);

        this.first = user1;
        this.second = user2;
    }

    public GameUser getEnemy(String user) {
        String enemyName = users.get(user).getEnemyName();
        return users.get(enemyName);
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
        return first.getMyScore() > second.getMyScore();
    }
}
