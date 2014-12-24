package frontend.AccoutServlets;

import backend.AccountService;
import backend.User;
import backend.enums.AccountEnum;
import org.json.JSONArray;
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
public class Scoreboard extends HttpServlet {
    public AccountService pool;
    private Map<String, String> mapMessage;

    public Scoreboard(AccountService p) {
        pool = p;
        mapMessage = ResourceFactory.instance().getResource("./data/Auth.xml");
    }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        JSONObject jsonObj = new JSONObject();
        try {
            if (pool.getScoreboard()==null) {
                //jsonObj.put("status", "2");
                //jsonObj.put("message", "no scoreboard");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().print(jsonObj.toString());
                return;
            }
            //jsonObj.put("status", "1");
            jsonObj= pool.getScoreboard();
        } catch(Exception e){}
        response.getWriter().print(jsonObj.toString());
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {//это кнопка залогинивания на форме авторизации
        response.setContentType("text/html;charset=utf-8");
        JSONObject json = new JSONObject();
        //TODO change scoreboard and create it depending on URL

    }

}
