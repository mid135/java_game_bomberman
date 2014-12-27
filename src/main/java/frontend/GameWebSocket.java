package frontend;


import backend.mechanics.GameMechanics;
import backend.mechanics.GameUser;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public String getMyName() {
        return myName;
    }

    public void startGame(GameUser user) throws JSONException{
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            jsonStart.put("enemyName", user.getEnemy().getMyName());
            session.getRemote().sendString(jsonStart.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void gameOver(String myName, int myScore, String oponentName, int oponentScore) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "finish");
            jsonStart.put("win", myScore == oponentScore ? "deadHead": (myScore>oponentScore ? "win": "fail") );
            jsonStart.put("myName", myName);
            jsonStart.put("myScore", myScore);
            jsonStart.put("oponentName", oponentName);
            jsonStart.put("oponentScore", oponentScore);
            session.getRemote().sendString(jsonStart.toString());
        } catch (Exception e) {
            //System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws JSONException{
        JSONObject inputJSON = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            inputJSON = (JSONObject)parser.parse(data.toString());

        } catch (ParseException e) {
        }
        gameMechanics.changePosition(myName,Integer.valueOf(inputJSON.get("delta").toString()));
    }

    @OnWebSocketConnect
    public void onOpen(Session session) throws JSONException{
        setSession(session);
        webSocketService.addUser(this);
        gameMechanics.addUser(myName);
    }

    public void setState(GameUser user) throws JSONException{
        JSONObject myJson = new JSONObject();
        myJson.put("status", "game");
        myJson.put("myState", user.getState());
        myJson.put("enemyState", user.getEnemy().getState());
        myJson.put("ballState",user.getBall().getState());
        try {
            session.getRemote().sendString(myJson.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        //TODO а тут что делать? - перейти на нужную вьюху!!!
      //  this.gameMechanics.gameOver(this.myName);
    }
}
