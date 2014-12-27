package frontend.mobile;

import backend.mechanics.GameMechanics;
import backend.mechanics.GameUser;
import frontend.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by titaevskiy.s on 23.12.14
 */
@WebSocket
public class MobileWebSocket {
    private String myName;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;
    private final static Set<MobileWebSocket> MOBILE_WEB_SOCKETS = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public MobileWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }



    @OnWebSocketConnect
    public void onConnetct(Session session) {
        this.session = session;
        MOBILE_WEB_SOCKETS.add(this);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        MOBILE_WEB_SOCKETS.remove(this);
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws JSONException {
        JSONObject inputJSON = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            inputJSON = (JSONObject)parser.parse(data.toString());

        } catch (ParseException e) {
        }
        gameMechanics.changePosition(myName, Integer.valueOf(inputJSON.get("delta").toString()));
    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws JSONException{
        setSession(session);
        webSocketService.addMobile(this);
        gameMechanics.addMobile(this.getMyName());
    }

    private void sendMessage(String data) {
        try {
            session.getRemote().sendString(data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSession(Session session) {
        this.session = session;
    }

    public String getMyName() {
        return myName;
    }

    public void setState() throws JSONException{
        JSONObject myJson = new JSONObject();
        myJson.put("status", "jostik");

        try {
            session.getRemote().sendString(myJson.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void startGame() throws JSONException{
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            session.getRemote().sendString(jsonStart.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

}
