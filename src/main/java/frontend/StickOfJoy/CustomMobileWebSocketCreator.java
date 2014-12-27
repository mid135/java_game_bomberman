package frontend.StickOfJoy;

import backend.AccountService;
import backend.mechanics.GameMechanics;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by mid on 26.12.14.
 */
public class CustomMobileWebSocketCreator implements WebSocketCreator{
    GameMechanics mech;
    AccountService acc;
    public CustomMobileWebSocketCreator(AccountService accountService, GameMechanics gameMechanics) {
        this.mech = gameMechanics;
        this.acc=accountService;
    }
    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        String sid = req.getSession().getId().toString();//достали сессию, теперь достаем юзера, которому нужен джойстик
        String name = this.acc.getArraySessionId().get(sid).getLogin();

        return new MobileWebSocket(sid,this.acc,this.mech);
    }
}
