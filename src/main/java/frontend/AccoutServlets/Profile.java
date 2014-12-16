package frontend.AccoutServlets;

import backend.AccountService;
import backend.User;
import backend.enums.AccountEnum;
import org.json.JSONException;
import org.json.JSONObject;
import resources.ResourceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.*
        ;
import java.io.IOException;
import java.util.Map;

/**
 * Created by mid on 13.12.14.
 */
public class Profile extends HttpServlet {
    public AccountService pool;
    private Map<String, String> mapMessage;

    public Profile(AccountService p) {
        pool = p;
        mapMessage = ResourceFactory.instance().getResource("./data/Auth.xml");
    }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        JSONObject jsonObj = new JSONObject();
        try {
            if (pool.checkLogIn(request)== AccountEnum.UserLoggedIn) {
                jsonObj.put("status", "2");
                jsonObj.put("message", "User has not logged in");
                response.getWriter().print(jsonObj.toString());
                return;
            }
            jsonObj.put("status", "1");
            jsonObj.put("user", pool.getArraySessionId().get(request.getSession().getId()).getLogin());
            jsonObj.put("email", pool.getArraySessionId().get(request.getSession().getId()).getEmail());
        } catch(JSONException e){}
        response.getWriter().print(jsonObj.toString());
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//это кнопка залогинивания на форме авторизации
        response.setContentType("text/html;charset=utf-8");
        JSONObject json = new JSONObject();
        //TODO change profile

    }

}
