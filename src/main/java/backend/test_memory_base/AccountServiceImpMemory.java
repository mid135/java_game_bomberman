package backend.test_memory_base;

import backend.AccountService;
import backend.User;
import backend.enums.AccountEnum;


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
public class AccountServiceImpMemory implements AccountService {

    private Map<String, User> arraySessionId = new HashMap<>();//все сессии пользователей - sessionId/User
    private Map<String, User> users = new HashMap<>();//все зарегистрированые юзеры - логин/User

    public AccountServiceImpMemory () {
        User us = new User("mid","123","mail");
        users.put("mid",us);
    }
    @Override
    public Map<String, User> getArraySessionId() {return  this.arraySessionId;};

    @Override
    public Map<String, User> getUsers() {return this.users;};

    @Override
    public AccountEnum checkRegistration(String userName) {//проверка регистрации пользователя

        if (users.containsKey(userName)) {
            return AccountEnum.UserRegistered;
        } else  {
            return AccountEnum.UserNotRegistered;
        }
    }
    @Override
    public AccountEnum checkLogIn (HttpServletRequest request) {//проверка залогинен ли пользватель
        if (arraySessionId.containsKey(request.getSession().getId())) {//есть ли у данного sid юзер
            return AccountEnum.UserLoggedIn;//TODO все не может быть так просто
        } else {
            return AccountEnum.UserNotLoggedIn;
        }
    }
    @Override
    public AccountEnum logIn(String login, String password,HttpServletRequest request) {//залогинивагние пользователя
        if (checkLogIn(request)==AccountEnum.UserNotLoggedIn) {
            if ( (this.checkRegistration(login) == AccountEnum.UserRegistered) ) {// TODO эта проверка не совсем корректна
                if (users.get(login).getPassword().equals(password)) {
                    arraySessionId.put(request.getSession().getId(), users.get(login));
                    return AccountEnum.LogInSuccess;
                } else {
                    return AccountEnum.LogInFail;
                }
            } else {
                return AccountEnum.LogInFail;
            }
        } else {
            if (users.get(login).getPassword().equals(password)) {
                return AccountEnum.UserLoggedIn;
            } else {
                arraySessionId.remove(request.getSession().getId());//если юзер уже залогинен но птается залогиниться еще раз - выбрасываем его из системы
                return AccountEnum.LogInFail;
            }

        }

    }
    @Override
    public AccountEnum logOff (HttpServletRequest request) {//разлогинивание пользователя
        if (checkLogIn(request)== AccountEnum.UserLoggedIn) {
            arraySessionId.remove(request.getSession().getId());
            return AccountEnum.LogOffSuccess;
        } else {
            return AccountEnum.LogOffFail;
        }
    }
    @Override
    public AccountEnum register(User user) {//регистрация пользователя
        if (users.containsKey(user.getLogin())) {
            return AccountEnum.UserAlreadyExists;
        } else {
            users.put(user.getLogin(), user);
            return AccountEnum.RegisterSuccess;
        }
    }

    //TODO напотом
    @Override
    public AccountEnum editProfile(User user) {
        return AccountEnum.EditSuccess;
    }

    @Override
    public String getNameByRequest(String sid) {
        return arraySessionId.get(sid).getLogin();
    }
}
