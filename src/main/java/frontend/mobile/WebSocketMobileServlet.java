package frontend.mobile;


import backend.AccountService;
import backend.mechanics.GameMechanics;
import frontend.CustomWebSocketCreator;
import frontend.WebSocketService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * This class represents a servlet starting a webSocket application
 */
@WebServlet(name = "WebSocketGameServlet", urlPatterns = {"/mobile"})
public class WebSocketMobileServlet extends WebSocketServlet {
    private final static int IDLE_TIME = 60 * 100;
    private AccountService accountService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public WebSocketMobileServlet(AccountService authService,
                                  GameMechanics gameMechanics,
                                  WebSocketService webSocketService) {
        this.accountService = authService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new CustomMobileWebSocketCreator(accountService, gameMechanics, webSocketService));
    }
}
