package frontend;

import backend.AccountService;
import backend.UserImplMemory;
import backend.enums.AccountEnum;
import backend.sql_base.dataSets.UserDataSet;
import backend.test_memory_base.User;
import resources.ResourceFactory;
import templater.PageGenerator;

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
    private Map<String, String> mapMessage;
    AccountService pool;
    Map<String, Object> pageVariables = new HashMap<>();

    public Register(AccountService users) {
        this.pool = users;
        mapMessage = ResourceFactory.instance().getResource("./data/Register.xml");
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        pageVariables.put("message",  mapMessage.get("welcome") );
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("registration.html", pageVariables));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//обработчик кнопки отлогинивания
        response.setContentType("text/html;charset=utf-8");
        User user = new UserDataSet(request.getParameter("login"),request.getParameter("password"),
                request.getParameter("email") == null ? "default@mail.ru": request.getParameter("email"));
       // User user = new UserImplMemory(request.getParameter("login"),request.getParameter("password"),
               // request.getParameter("email") == null ? "default@mail.ru": request.getParameter("email"));

        if(pool.checkRegistration(user.getLogin()) == AccountEnum.UserRegistered ) {
            pageVariables.put("message",mapMessage.get("userExist"));
        } else {
            if (pool.register(user) == AccountEnum.RegisterSuccess) {
                pageVariables.put("message",mapMessage.get("success"));
            } else {
                pageVariables.put("message",mapMessage.get("fail"));
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
    }
}
