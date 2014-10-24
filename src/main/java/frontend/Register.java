package frontend;

import backend.AccountService;
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
 * Created by mid on 27.09.14.
 */
public class Register extends HttpServlet {
    String message;
    AccountService pool;
    Map<String, Object> pageVariables = new HashMap<>();

    public Register(AccountService users) {
        this.pool = users;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        pageVariables.put("message", message == null ? "" : message);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("registration.html", pageVariables));
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//обработчик кнопки отлогинивания
        response.setContentType("text/html;charset=utf-8");
        User user = new User(request.getParameter("login"),request.getParameter("password"),
                request.getParameter("email") == null ? "Hello": request.getParameter("email"));

        if(pool.checkRegistration(user.getLogin())) {
            pageVariables.put("message","Пользователь с таким именем уже зерегистрирован в системе!");
        } else {
            if (pool.register(user)) {
                pageVariables.put("message","Поздравляем, вы зарегистированы!");
            } else {
                pageVariables.put("message","Fail.Что-то пошло не так.");
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
    }
}
