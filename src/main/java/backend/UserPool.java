package backend;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mid on 30.09.14.
 * //в этом классе происходит обработка всех действий над пользователями - логин, регистрация, итд
 *
 */

//TODO предусмотреть случаи когда
    // от одного компа логинится сверху кто-то
public class UserPool {
    private User user;


    public Map<String, User> getArraySessionId() {
        return arraySessionId;
    }
    private Map<String, User> arraySessionId = new HashMap<>();//все сессии пользователей - sessionId/User

    public Map<String, User> getUsers() {
        return users;
    }
    private Map<String, User> users = new HashMap<>();//все зарегистрированые юзеры - логин/User

    //private Map<String, Boolean> userStatus = new HashMap<>();//статусы пользователей

    public boolean checkRegistration(String userName) {//проверка регистрации пользователя
        if (users.containsKey(userName)) {
            return true;
        } else  {
            return false;
        }
    }

    public boolean checkLogIn (HttpServletRequest request) {//проверка залогинен ли пользватель
        if (arraySessionId.containsKey(request.getSession().getId())) {//есть ли у данного sid юзер
            return true;//TODO все не может быть так просто
        } else {
            return false;
        }
    }

    public boolean logIn(String login, String password,HttpServletRequest request) {//залогинивагние пользователя
        if (!checkLogIn(request)) {
            if (this.checkRegistration(login) && !arraySessionId.containsValue(login)) {// TODO эта проверка не совсем корректна
                if (users.get(login).password.equals(password)) {
                    arraySessionId.put(request.getSession().getId(), users.get(login));
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;//TODO обдумать что делать, если юзер уже залогинен
        }

    }

    public boolean logOff (HttpServletRequest request) {//разлогинивание пользователя
        User cur=new User();
        if (arraySessionId.containsKey(request.getSession().getId())) {
            cur = arraySessionId.get(request.getSession().getId());
            if (this.checkRegistration(cur.login) && this.checkLogIn(request)) {
                arraySessionId.remove(request.getSession().getId());
                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean register(User user) {//регистрация пользователя
        if (users.containsKey(user.login)) {
            return false;
        } else {
            users.put(user.login, user);
            return true;
        }
    }
}
