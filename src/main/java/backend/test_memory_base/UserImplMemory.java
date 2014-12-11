package backend.test_memory_base;

import backend.User;

/**
 * Created by narek on 26.09.14.
 */
public class UserImplMemory implements User {
    private String login;
    private String password;
    private String email;

    public UserImplMemory(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }
    @Override
    public String getLogin() {
        return login;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public void setLogin(String login) {
        this.login = login;
    }
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    private String enemyName;
    private int enemyScore;
    private int myScore;

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
        return login;
    }

}
