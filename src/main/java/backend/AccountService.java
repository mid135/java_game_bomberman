package backend;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mid on 23.10.14.
 * Это интерфейс аккаунт сервиса, к которому подключаются классы по небходимост(или тестовый класс в memmory базой
 * или класс для взаимодействия с БД
 */

//API еще не окончательно, додумать!
public interface AccountService {

    public boolean checkRegistration(String userName) ;//проверка регистрации пользователя
    public boolean checkLogIn (HttpServletRequest request) ;//проверка залогинен ли пользватель
    public boolean logIn(String login, String password,HttpServletRequest request);//залогинивагние пользователя
    public boolean logOff (HttpServletRequest request) ;//разлогинивание пользователя
    public boolean register(User user);//регистрация пользователя
    public boolean editProfile(User user);//изменение профиля
}
