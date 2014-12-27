package frontend.mobile;

import backend.AccountService;
import backend.mechanics.GameMechanics;
import frontend.GameWebSocket;
import frontend.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by titaevskiy.s on 23.12.14
 */
public class CustomMobileWebSocketCreator implements WebSocketCreator {

    private AccountService authService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public CustomMobileWebSocketCreator(AccountService authService,
                                  GameMechanics gameMechanics,
                                  WebSocketService webSocketService) {
        this.authService = authService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        String sessionId = req.getHttpServletRequest().getSession().getId();
        String name;
        try {
            name = authService.getArraySessionId().get(sessionId).getLogin();
        } catch (Exception e) {//иначе аноним
            name = "anonymus";
        }
        return new MobileWebSocket(name, gameMechanics, webSocketService);
    }
}
