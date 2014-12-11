package frontend.AccoutServlets;

import backend.AccountService;
import backend.enums.AccountEnum;
import org.json.JSONException;
import org.json.JSONObject;
import resources.ResourceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
                      HttpServletResponse  response) throws ServletException, IOException {
        JSONObject resp = new JSONObject();
        try {
            if (pool.checkLogIn(request) == AccountEnum.UserLoggedIn) {
                resp.put("message", "OK");
            } else {
                resp.put("message", "FAIL");
            }
        } catch (JSONException e) {

        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//это кнопка залогинивания на форме авторизации
        response.setContentType("text/html;charset=utf-8");
        JSONObject json = new JSONObject();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
        switch (this.pool.logIn(login,password, request) ) {
            case LogInSuccess: {
               json.put("status", "1");
                break;
            }
            case UserLoggedIn: {
                json.put("status", "1");
                break;
            }
            case LogInFail: {
                json.put("status", "0");
                break;
            }
            default: {
                json.put("status", "0");
                break;
            }
        }} catch (JSONException e) {

        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(json.toString());

    }
}
