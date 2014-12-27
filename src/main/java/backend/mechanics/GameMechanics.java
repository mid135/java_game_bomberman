package backend.mechanics;

/**
 * Created by mid on 08.12.14.
 */



import backend.AccountService;
import frontend.WebSocketService;
import org.json.JSONException;
import utils.TimeHelper;

import java.sql.SQLException;
import java.util.*;


public class GameMechanics {
    private static final int STEP_TIME = 50;

    private static final int gameTime = 40 * 1000;

    private static final int speed_inc = 1;//TODO cкорость постепенно увеличивается

    private boolean indikateChangeScore = true;

    private boolean finishGame = false;

    public WebSocketService getWebSocketService() {
        return webSocketService;
    }

    private WebSocketService webSocketService;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private String waiter;

    private AccountService pool;
    public GameMechanics(WebSocketService webSocketService, AccountService p) {
        this.webSocketService = webSocketService;
        this.pool=p;
    }

    public void addUser(String user) throws JSONException{
        if (waiter != null) {
            starGame(user);
            waiter = null;
        } else {
            waiter = user;
        }
    }

    public void changePosition(String userName,Integer delta) throws JSONException{
        GameSession myGameSession = nameToGame.get(userName);

        GameUser myUser = myGameSession.getSelf(userName);
        if (myUser.getPlatform().getX()+ (delta * 2) > 257 || myUser.getPlatform().getX()+ (delta * 2) < 20 ){

        }else {
            myUser.getPlatform().setX(myUser.getPlatform().getX() + (delta * 2));
        }

        /*if (myUser.getBall().getX()==0) {
            webSocketService.notifyGameOver(myGameSession.getFirst(), true);
            webSocketService.notifyGameOver(myGameSession.getSecond(), false);
        } else if(myUser.getEnemy().getBall().getX()==500) {
            webSocketService.notifyGameOver(myGameSession.getFirst(), false);
            webSocketService.notifyGameOver(myGameSession.getSecond(), true);
        }*/
        webSocketService.notifyNewState(myUser,myUser.getEnemy());

    }


    public void run() throws JSONException {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() throws JSONException {
        for (GameSession session : allSessions) {
            GameUser myUser = session.getFirst();
            if ( myUser.getBall().getX() > 25 ) {
                if (myUser.getBall().getX() < 290 ) {
                    myUser.getBall().setX(session.getFirst().getBall().getX()+session.getFirst().getBall().getVx());
                } else {
                    session.getFirst().getBall().setVx(session.getFirst().getBall().getVx()*(-1));
                    myUser.getBall().setX(session.getFirst().getBall().getX()+session.getFirst().getBall().getVx());
                }
            } else {
                session.getFirst().getBall().setVx(session.getFirst().getBall().getVx()*(-1));
                myUser.getBall().setX(session.getFirst().getBall().getX()+session.getFirst().getBall().getVx());
            }
            if ( myUser.getBall().getY() > 10 ) {
                if (myUser.getBall().getY() < 140 ) {

                    if (Math.abs(myUser.getPlatform().getX()+25-myUser.getBall().getX()) < 25 &&
                            Math.abs(myUser.getPlatform().getY()-myUser.getBall().getY()) < 5 ||
                            Math.abs(myUser.getEnemy().getPlatform().getX()+25-myUser.getBall().getX()) < 25 &&
                            Math.abs(myUser.getEnemy().getPlatform().getY()+5-myUser.getBall().getY()) < 5
                            ) {
                        session.getFirst().getBall().setVy(session.getFirst().getBall().getVy()*(-1));
                        myUser.getBall().setY(session.getFirst().getBall().getY() + session.getFirst().getBall().getVy());
                        indikateChangeScore = true;
                    }else {
                        myUser.getBall().setY(session.getFirst().getBall().getY() + session.getFirst().getBall().getVy());
                    }

                } else {
                    if (indikateChangeScore && !finishGame) {
                        myUser.getEnemy().increment();
                        indikateChangeScore = false;
                    }
                    session.getFirst().getBall().setVy(session.getFirst().getBall().getVy()*(-1));
                    myUser.getBall().setY(session.getFirst().getBall().getY() + session.getFirst().getBall().getVy());
                }


            } else {
                if (indikateChangeScore && !finishGame) {
                    myUser.increment();
                    indikateChangeScore = false;
                }
                session.getFirst().getBall().setVy(session.getFirst().getBall().getVy()*(-1));
                myUser.getBall().setY(session.getFirst().getBall().getY() + session.getFirst().getBall().getVy());
            }

            webSocketService.notifyNewState(myUser,myUser.getEnemy());

            if (session.getSessionTime() > gameTime) {
                if (!finishGame) {
                    pool.saveScore(session.getFirst().getUser().getId(), session.getFirst().getMyName(), session.getFirst().getScore());
                    pool.saveScore(session.getSecond().getUser().getId(), session.getSecond().getMyName(), session.getSecond().getScore());
                }
                finishGame = true;
                webSocketService.notifyGameOver(session.getFirst());
                webSocketService.notifyGameOver(session.getSecond());

            }
        }
    }

    private void starGame(String first) throws JSONException{
        String second = waiter;
        GameSession gameSession = new GameSession(pool,first, second);
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));

        webSocketService.notifyNewState(gameSession.getSelf(first),gameSession.getSelf(second));
       // webSocketService.notifyNewState(gameSession.getSelf(second),gameSession.getSelf(first));
    }
    public void gameOver(String name) {
        //TODO запись очков в БД
        GameSession ses = nameToGame.get(name);
        allSessions.remove(ses);
        try {
        String first = ses.getFirst().getMyName();
        webSocketService.notifyGameOver(ses.getFirst());
        nameToGame.remove(first);

            String second = ses.getSecond().getMyName();
            webSocketService.notifyGameOver(ses.getSecond());
            nameToGame.remove(second);
        } catch (NullPointerException e) {
            System.console().writer().print("nullpointer");
        }





    }
}

