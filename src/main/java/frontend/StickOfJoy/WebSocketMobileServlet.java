package frontend.StickOfJoy;

import backend.AccountService;
import backend.mechanics.GameMechanics;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Created by mid on 26.12.14.
 */
public class WebSocketMobileServlet extends WebSocketServlet  {
    private static final String pageUrl = "/mobile";
    private static final int IDLE_TIME = 60 * 1000;
    AccountService pool;
    GameMechanics mech;
    public WebSocketMobileServlet(AccountService p,GameMechanics m) {
        this.pool=p;this.mech=m;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new CustomMobileWebSocketCreator(this.pool,this.mech));
    }
}
