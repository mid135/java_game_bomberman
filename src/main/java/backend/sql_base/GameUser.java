package backend.sql_base;

import backend.AccountService;
import backend.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mid on 11.12.14.
 */
public class GameUser {
    private String name;
    private String enemyName;
    private int enemyScore;
    private int myScore;
    private User user;

public void setUser(User nam) {
    this.user = nam;
}

    public String getEnemyName() {
        return enemyName;
    }


    public int getMyScore() {
        return myScore;
    }

    public int getEnemyScore() {
        return enemyScore;
    }

    public void incrementMyScore() {
        myScore++;
    }

    public void incrementEnemyScore() {
        enemyScore++;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }
    public String getMyName() {
        try {
            return user.getLogin();
        } catch (NullPointerException e) {
            return "anon";
        }
    }
}
