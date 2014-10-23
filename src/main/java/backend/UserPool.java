package backend;

import backend.test_memory_base.UserPool_mem;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mid on 23.10.14.
 */
public class UserPool implements AccountService {
    protected Map<String, User> arraySessionId = new HashMap<>();//все сессии пользователей - sessionId/User
    protected Map<String, User> users = new HashMap<>();//все зарегистрированые юзеры - логин/User

    public Map<String, User> getArraySessionId() { return arraySessionId;}
    public Map<String, User> getUsers() { return users; }

    public boolean checkRegistration(String userName) {return true;}
    public boolean checkLogIn (HttpServletRequest request) {return true; }
    public boolean logIn(String login, String password,HttpServletRequest request){return true;}
    public boolean logOff (HttpServletRequest request){ return true; }
    public boolean register(User user){ return true; }
    public boolean editProfile(User user){ return true; }
}
