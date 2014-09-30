package frontend;

import backend.UserPool;
import templater.PageGenerator;
import templater.Pages;
import templater.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static templater.Pages.*;

/**
 * Created by narek on 13.09.14.
 */
public class Auth extends HttpServlet {
    public Auth(UserPool p) {
        pool = p;
    }
    private User user;//текущий пользователь
    public UserPool pool;

    private String message = "Введите логин и пароль для входа";
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", message == null ? "" : message);
        response.setStatus(HttpServletResponse.SC_OK);
        if (pool.checkLogIn(request)) {
            User user = new User();
            user = pool.getArraySessionId().get(request.getSession().getId());
            pageVariables.put("login",pool.getUsers().get(user.login).login);
            pageVariables.put("password",pool.getUsers().get(user.login).password);
            pageVariables.put("email",pool.getUsers().get(user.login).email);
            response.getWriter().println(PageGenerator.getPage("profileUser.html", pageVariables));
        } else {
            response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
        }
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//это кнопка залогинивания на форме авторизации
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();

        user = new User();
        user.login = request.getParameter("login");
        user.password = request.getParameter("password");

        if ((user.login == null || user.login.isEmpty()) && (user.password == null || user.password.isEmpty())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        if (this.pool.logIn(user.login, request)) {
            //успех залогинивания
            pageVariables.put("message","Вход успешен");

            pageVariables.put("login",pool.getUsers().get(user.login).login);
            pageVariables.put("password",pool.getUsers().get(user.login).password);
            pageVariables.put("email",pool.getUsers().get(user.login).email);
            response.getWriter().println(PageGenerator.getPage("profileUser.html", pageVariables));
        } else {
            pageVariables.put("message","Неправильный логин и/или пароль");
            response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
        }


    }
}
