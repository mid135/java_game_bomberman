package backend.mechanics;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mid on 16.12.14.
 */
public class Shape {
    public Shape(double x, double y, double Vx, double Vy){this.x= x;this.y=y;this.Vx = Vx; this.Vy = Vy;}

    private double x;
    private double y;
    private double Vx;
    private double Vy;

    public JSONObject getState() {
        JSONObject out = new JSONObject();
        try {
            out.put("x", this.x);
            out.put("y", this.y);

        } catch(JSONException e) {}
        return out;
    }

    public double getX() {       return x;    }

    public double getY() {       return y;    }

    public double getVx() {
        return Vx;
    }

    public double getVy() {
        return Vy;
    }

    public void setX(double x) {        this.x = x;    }

    public void setY(double y) {        this.y = y;    }

    public void setVx(double vx) {
        Vx = vx;
    }

    public void setVy(double vy) {
        Vy = vy;
    }
}
