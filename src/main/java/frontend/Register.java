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
 * Created by mid on 27.09.14.
 */
public class Register extends HttpServlet {
    String message;
    AccountService pool;
    Map<String, Object> pageVariables = new HashMap<>();

    public Register(AccountService users) {
        this.pool = users;
    }


    private void sendPage(HttpServletResponse response, Map<String, Object> pageVariables, String pageName) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage(pageName, pageVariables));
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        pageVariables.put("message", message == null ? "" : message);
        sendPage(response,pageVariables,"registration.html");
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//обработчик кнопки отлогинивания

        User user = new User(request.getParameter("login"),request.getParameter("password"),
                request.getParameter("email") == null ? "Hello": request.getParameter("email"));

        if(pool.checkRegistration(user.getLogin()) == AccountEnum.UserRegistered ) {
            pageVariables.put("message","Пользователь с таким именем уже зерегистрирован в системе!");
        } else {
            if (pool.register(user) == AccountEnum.RegisterSuccess) {
                pageVariables.put("message","Поздравляем, вы зарегистированы!");
            } else {
                pageVariables.put("message","Fail.Что-то пошло не так.");
            }
        }
        sendPage(response,pageVariables,"authform.html");
    }
}
