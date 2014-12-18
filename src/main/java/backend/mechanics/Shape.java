package backend.mechanics;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mid on 16.12.14.
 */
public class Shape {
    public Shape(int x, int y, int sp){this.x= x;this.y=y;this.speed=sp;}

    private int x;
    private int y;
    private int speed;//can be unused

    public JSONObject getState() {
        JSONObject out = new JSONObject();
        try {
            out.put("x", this.x);
            out.put("y", this.y);

        } catch(JSONException e) {}
        return out;
    }

    public int getX() {       return x;    }

    public int getY() {       return y;    }

    public int getSpeed() {        return speed;    }

    public void setX(int x) {        this.x = x;    }

    public void setY(int y) {        this.y = y;    }

    public void setSpeed(int speed) {        this.speed = speed;    }

}
