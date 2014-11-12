package backend.Mechanics;

/**
 * Created by mid on 12.11.14.
 */
public class UserGameState {
    private final GameUser myGameUser;
    private final GameUser enemyGameUser;
    private final int whoseTurn;
    private final int[] field;
    private final int winner;
    private final boolean isFinished;
    public UserGameState(GameUser myGameUser, GameUser enemyGameUser, int whoseTurn, int[] field, int winner, boolean isFinished) {
        this.myGameUser = myGameUser;
        this.enemyGameUser = enemyGameUser;
        this.whoseTurn = whoseTurn;
        this.field = field;
        this.winner = winner;
        this.isFinished = isFinished;
    }
    public GameUser getMyGameUser() {
        return myGameUser;
    }
    public GameUser getEnemyGameUser() {
        return enemyGameUser;
    }
    public int getWhoseTurn() {
        return whoseTurn;
    }
    public int[] getField() {
        return field;
    }
    public int getWinner() {
        return winner;
    }
    public boolean isFinished() {
        return isFinished;
    }
}
