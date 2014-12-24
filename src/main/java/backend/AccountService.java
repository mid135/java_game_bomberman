package backend;

import backend.enums.AccountEnum;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by mid on 23.10.14.
 * Это интерфейс аккаунт сервиса, к которому подключаются классы по небходимост(или тестовый класс в memmory базой
 * или класс для взаимодействия с БД
 */

//API еще не окончательно, додумать!
public interface AccountService {

    public Map<String, User> getArraySessionId();
    public Map<String, User> getUsers();

    public AccountEnum checkRegistration(String userName) ;//проверка регистрации пользователя
    public AccountEnum checkLogIn (HttpServletRequest request) ;//проверка залогинен ли пользватель
    public AccountEnum logIn(String login, String password,HttpServletRequest request);//залогинивагние пользователя
    public AccountEnum logOff (HttpServletRequest request) ;//разлогинивание пользователя
    public AccountEnum register(User userImplMemory);//регистрация пользователя
    public AccountEnum editProfile(User userImplMemory);//изменение профиля
    public JSONObject getScoreboard();//получение очков
    public void saveScore(long userId, String userName, int score);
}
