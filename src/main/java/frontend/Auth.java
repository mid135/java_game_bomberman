package frontend;

import backend.AccountService;
import backend.enums.AccountEnum;
import resources.ResourceFactory;
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
    private Map<String, String> mapMessage;

    public Auth(AccountService p) {
        pool = p;
        mapMessage = ResourceFactory.instance().getResource("./data/Auth.xml");
    }



    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", mapMessage == null ? "" : mapMessage.get("welcome"));
        response.setStatus(HttpServletResponse.SC_OK);
        if (pool.checkLogIn(request) == AccountEnum.UserLoggedIn ) {
            User user;
            user = pool.getArraySessionId().get(request.getSession().getId());
            pageVariables.put("login",pool.getUsers().get(user.getLogin()).getLogin());
            pageVariables.put("password",pool.getUsers().get(user.getLogin()).getPassword());
            pageVariables.put("email",pool.getUsers().get(user.getLogin()).getEmail());
            response.getWriter().println(PageGenerator.getPage("profileUser.html", pageVariables));
        } else {
            response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
        }
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//это кнопка залогинивания на форме авторизации
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if ((login == null || login.isEmpty()) && (password == null || password.isEmpty())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        if (this.pool.logIn(login,password, request) == AccountEnum.LogInSuccess ) {
            //успех залогинивания
            pageVariables.put("message",mapMessage.get("success"));

            pageVariables.put("login",pool.getUsers().get(login).getLogin());
            pageVariables.put("password",pool.getUsers().get(login).getPassword());
            pageVariables.put("email",pool.getUsers().get(login).getEmail());
            response.getWriter().println(PageGenerator.getPage("profileUser.html", pageVariables));
        } else {
            pageVariables.put("message",mapMessage.get("fail"));
            response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
        }


    }
}
