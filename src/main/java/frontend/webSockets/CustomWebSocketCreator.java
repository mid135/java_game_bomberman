package frontend.webSockets;

import backend.AccountService;
import backend.mechanics.GameMechanics;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * @author v.chibrikov
 */
public class CustomWebSocketCreator implements WebSocketCreator {
    private AccountService accountService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public CustomWebSocketCreator(AccountService accountService,
                                  GameMechanics gameMechanics,
                                  WebSocketService webSocketService) {
        this.accountService = accountService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        String sessionId = req.getHttpServletRequest().getSession().getId();

        String name = accountService.getNameByRequest(sessionId);
        return new GameWebSocket(name, gameMechanics, webSocketService);
    }
}
