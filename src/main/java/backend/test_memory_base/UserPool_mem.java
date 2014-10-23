package backend.test_memory_base;

import backend.AccountService;
import backend.User;
import backend.UserPool;

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
public class UserPool_mem extends UserPool {
    @Override
    public boolean checkRegistration(String userName) {//проверка регистрации пользователя

        if (users.containsKey(userName)) {
            return true;
        } else  {
            return false;
        }
    }
    @Override
    public boolean checkLogIn (HttpServletRequest request) {//проверка залогинен ли пользватель
        if (arraySessionId.containsKey(request.getSession().getId())) {//есть ли у данного sid юзер
            return true;//TODO все не может быть так просто
        } else {
            return false;
        }
    }
    @Override
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
    @Override
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
    @Override
    public boolean register(User user) {//регистрация пользователя
        if (users.containsKey(user.login)) {
            return false;
        } else {
            users.put(user.login, user);
            return true;
        }
    }
    @Override
    public boolean editProfile(User user) {
        return true;
    }
}
