package frontend;

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
public class SignUpServlet extends HttpServlet {
    private String mesage = "Введите логин и пароль для входа";
    private User user;

    public Map<String, Pages> getPages() {
        return pages;
    }

    public Map<String, User> getArraySessionId() {
        return arraySessionId;
    }

    public Map<String, Boolean> getUsersStatusAuthorization() {
        return usersStatusAuthorization;
    }

    private Map<String, Pages> pages = new HashMap<>();
    private Map<String, User> arraySessionId = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private Map<String, Boolean> usersStatusAuthorization = new HashMap<>();


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("mesage", mesage == null ? "" : mesage);
        if (pages.containsKey(request.getSession().getId())){
            switch(pages.get(request.getSession().getId())) {
                case authform: response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
                    break;
                case authorization: response.getWriter().println(PageGenerator.getPage("authorization.html", pageVariables));
                    break;
                case profileUser: pageVariables.put("mesage", "Добро пожаловать!");
                    pageVariables.put("Login",arraySessionId.get(request.getSession().getId()).login);
                    pageVariables.put("Email",arraySessionId.get(request.getSession().getId()).email);
                    pageVariables.put("Password",arraySessionId.get(request.getSession().getId()).password);
                    response.getWriter().println(PageGenerator.getPage("profileUser.html", pageVariables));
                    break;
                default: response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
                    break;
            }
        } else response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);

    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        user = new User();
        user.login = request.getParameter("login");
        user.password = request.getParameter("password");


        user.email =  request.getParameter("email") == null ? "Hello": request.getParameter("email");




        if ((user.login == null || user.login.isEmpty()) && (user.password == null || user.password.isEmpty())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        Map<String, Object> pageVariables = new HashMap<>();
        if (!arraySessionId.containsKey(request.getSession().getId())){
            if (users.containsKey(user.login)) {
                if (users.get(user.login).password.equals(user.password)) {
                    if (usersStatusAuthorization.get(user.login).equals(false)){
                        pageVariables.put("mesage", "Вход выполнен успешно");
                        arraySessionId.put(request.getSession().getId(), user);
                        usersStatusAuthorization.put(user.login, true);
                        pages.put(request.getSession().getId(), profileUser);
                        pageVariables.put("Login", user.login);
                        pageVariables.put("Password", users.get(user.login).password);
                        pageVariables.put("Email", users.get(user.login).email);
                    } else {
                        pages.put(request.getSession().getId(), profileUser);
                        arraySessionId.put(request.getSession().getId(), users.get(user.login));
                        pageVariables.put("mesage", "Вход выполнен успешно");
                        pageVariables.put("Login", user.login);
                        pageVariables.put("Password", users.get(user.login).password);
                        pageVariables.put("Email", users.get(user.login).email);
                      }

                } else {
                    pageVariables.put("mesage", "Неверный пароль !!!");
                    pages.replace(request.getSession().getId(), authform);
                }
            } else {
                if (!pages.containsKey(request.getSession().getId())) {
                    pageVariables.put("mesage", "Пользователь не существует. Введите данные для регистрации");
                    pages.put(request.getSession().getId(), authorization);

                } else {
                    users.put(user.login, user);
                    usersStatusAuthorization.put(user.login, false);
                    pages.replace(request.getSession().getId(), authform);
                    pageVariables.put("mesage", "Логин " + user.login + " зарегистрирован");
                }
            }
        } else {
            pages.replace(request.getSession().getId(), profileUser);
            pageVariables.put("Login", user.login);
            pageVariables.put("Password", arraySessionId.get(request.getSession().getId()).password);
            pageVariables.put("Email", arraySessionId.get(request.getSession().getId()).email);
        }

            switch(pages.get(request.getSession().getId())) {
                case authform: response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
                    break;
                case authorization: response.getWriter().println(PageGenerator.getPage("authorization.html", pageVariables));
                    break;
                case profileUser: response.getWriter().println(PageGenerator.getPage("profileUser.html", pageVariables));
                    break;
                default: response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
                    break;
            }

    }
}
