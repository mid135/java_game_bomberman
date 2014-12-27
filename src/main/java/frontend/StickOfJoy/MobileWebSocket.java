package frontend.StickOfJoy;

/**
 * Created by mid on 26.12.14.
 */


        import backend.AccountService;
        import backend.User;
        import backend.mechanics.GameMechanics;
        import org.eclipse.jetty.websocket.api.Session;
        import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
        import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
        import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
        import org.eclipse.jetty.websocket.api.annotations.WebSocket;
        import org.json.simple.JSONObject;
        import org.json.simple.parser.JSONParser;
        import org.json.simple.parser.ParseException;

        import java.io.IOException;
        import java.util.Collections;
        import java.util.Set;
        import java.util.concurrent.ConcurrentHashMap;


@WebSocket
public class MobileWebSocket {

    private final static Set<MobileWebSocket> MOBILE_WEB_SOCKETS = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private Session session;
    private GameMechanics gameMechanics;
    private AccountService accountService;
    private User user;
    String sid;//sis http сесии для доступа к карту джойстиков

    public MobileWebSocket(String sid,AccountService accountService,GameMechanics gameMechanics) {
        this.gameMechanics=gameMechanics;
        this.accountService=accountService;
        this.sid=sid;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        User us = this.accountService.getArraySessionId().get(this.sid);
        this.user=us;
        MOBILE_WEB_SOCKETS.add(this);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        MOBILE_WEB_SOCKETS.remove(this);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        changePosition(data);
    }

    private void changePosition(String data) {
        JSONObject inputJSON = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            inputJSON = (JSONObject)parser.parse(data.toString());

        } catch (ParseException e) {
        }
        try {
            this.gameMechanics.changePosition(user.getLogin(),Integer.valueOf(inputJSON.get("delta").toString()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

