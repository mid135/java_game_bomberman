package backend.Mechanics;

/**
 * Created by mid on 12.11.14.
 */
import backend.GameMechanics;
import backend.WebSocketService;

public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 1000;
    private final WebSocketService webSocketService;
    private String waiter;
    public GameMechanicsImpl(WebSocketService webSocketService) { this.webSocketService = webSocketService;}
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {

    }
    @Override
    public void waitForEnemy(String login) {

    }
    @Override
    public void doTurn(String login, int position) {

    }
    @Override
    public void closeGameSession(String login) {

    }
    private void startGame(String waiter, String login) {

    }
    private void gameOver(UserGameState first, UserGameState second) {
        webSocketService.notifyGameOver(first);
        webSocketService.notifyGameOver(second);
    }
    private void gameUpdate(UserGameState first, UserGameState second) {
        webSocketService.notifyUpdateGameState(first);
        webSocketService.notifyUpdateGameState(second);
    }
    private void clearGameSessions() {

    }
}
