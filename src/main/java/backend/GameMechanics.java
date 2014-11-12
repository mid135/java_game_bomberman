package backend;

/**
 * Created by mid on 12.11.14.
 */
public interface GameMechanics {
    void run();
    void waitForEnemy(String login);
    void doTurn(String login, int position);
    void closeGameSession(String login);
}
