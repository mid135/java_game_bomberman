package frontend.AccoutServlets;

import backend.AccountService;
import backend.enums.AccountEnum;
import backend.sql_base.dataSets.UserDataSet;
import backend.User;
import org.json.JSONException;
import org.json.JSONObject;
import resources.ResourceFactory;

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
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//обработчик кнопки отлогинивания
        response.setContentType("text/html;charset=utf-8");
        JSONObject json = new JSONObject();
        User user = new UserDataSet(request.getParameter("login"),request.getParameter("password"),
                request.getParameter("email") == null ? "default@mail.ru": request.getParameter("email"));
       // User user = new UserImplMemory(request.getParameter("login"),request.getParameter("password"),
               // request.getParameter("email") == null ? "default@mail.ru": request.getParameter("email"));
        try {
            if (pool.checkRegistration(user.getLogin()) == AccountEnum.UserRegistered) {
                json.put("status", "0");
                json.put("message",mapMessage.get("userExist"));
            } else {
                if (pool.register(user) == AccountEnum.RegisterSuccess) {
                    json.put("status", "1");
                    json.put("message",mapMessage.get("success"));
                } else {
                    json.put("status", "0");
                    json.put("message",mapMessage.get("fail"));
                }
            }
        } catch (JSONException e) {

        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(json.toString());
    }
}
