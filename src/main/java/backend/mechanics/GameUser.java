package backend.mechanics;

/**
 * @author v.chibrikov
 */
public class GameUser {
    private final String myName;
    private String enemyName;
    private int myScore = 0;
    private int enemyScore = 0;
    private int position_x;
    private int position_y;

    public GameUser(String myName) {
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
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

    public void setPosition_x(int position_x) { this.position_x = position_x; }

    public void setPosition_y(int position_y) { this.position_y = position_y; }

    public void incrementMyScore() {
        myScore++;
    }

    public void incrementEnemyScore() {
        enemyScore++;
    }

    public boolean move(int direction) {//1 up, 2 right,3 down, 4 left
        switch (direction){
            case 1: {

            }
            case 2: {

            }
            case 3: {

            }
            case 4: {

            }
        }
        return true;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }
}
