package frontend;

import backend.AccountService;
import backend.enums.AccountEnum;
import templater.PageGenerator;
import backend.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by narek on 13.09.14.
 */
public class Auth extends HttpServlet {
    public AccountService pool;
    private String message = "Введите логин и пароль для входа";

    public Auth(AccountService p) {
        pool = p;
    }


    private void sendPage(HttpServletResponse response, Map<String, Object> pageVariables, String pageName) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage(pageName, pageVariables));
    }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();
        if (pool.checkLogIn(request) == AccountEnum.UserLoggedIn ) {
            User user;
            user = pool.getArraySessionId().get(request.getSession().getId());
            pageVariables.put("login",pool.getUsers().get(user.getLogin()).getLogin());
            pageVariables.put("password",pool.getUsers().get(user.getLogin()).getPassword());
            pageVariables.put("email",pool.getUsers().get(user.getLogin()).getEmail());
            sendPage(response,pageVariables,"profileUser");
        } else {
            pageVariables.put("message", "Здавствуйте, незнакомец!");
            sendPage(response,pageVariables,"authform.html");
        }
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//это кнопка залогинивания на форме авторизации

        Map<String, Object> pageVariables = new HashMap<>();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if ((login == null || login.isEmpty()) && (password == null || password.isEmpty())) {
            pageVariables.put("message","Введите пароль и логин! Пустые поля недопустимы!");
            sendPage(response,pageVariables,"authform.html");
            return;
        }

        if (this.pool.logIn(login,password, request) == AccountEnum.LogInSuccess ) {
            //успех залогинивания
            pageVariables.put("login",pool.getUsers().get(login).getLogin());
            pageVariables.put("password",pool.getUsers().get(login).getPassword());
            pageVariables.put("email",pool.getUsers().get(login).getEmail());
            pageVariables.put("message","Вход выполнен успешно");
            sendPage(response,pageVariables,"profileUser.html");
        } else {
            pageVariables.put("message","Неправильный логин и/или пароль");
            sendPage(response,pageVariables,"authform.html");
        }


    }
}
