package backend.mechanics;

import backend.AccountService;
import backend.User;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mid on 11.12.14.
 */

//мяч будет храниться у обоих юзеров в виде координат, надо их туда писать!!!
public class GameUser {
    private String name;
    private GameUser enemy;
    private Shape ball;
    private Shape platform;
    private User user;

    public GameUser(int x,int y,Shape b) {
        this.platform = new Shape(x,y,0);
        this.ball = b;
    }
    public Shape getBall() {        return ball;    }

    public Shape getPlatform() {        return platform;    }



    public void setUser(User nam) {
        this.name=nam.getLogin();
        this.user = nam;
}

    public GameUser getEnemy() {
        return enemy;
    }

    public void setEnemyName(GameUser enemyName) {
        this.enemy = enemyName;
    }

    public JSONObject getState() {
        JSONObject resp = new JSONObject();
        try {
            resp.put("x",platform.getX());
            resp.put("y",platform.getY());
        } catch(JSONException e) {}

        return resp;
    }


    public String getMyName() {
        return name;
    }
}
