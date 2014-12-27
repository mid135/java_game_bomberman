package frontend.AccoutServlets;

import backend.AccountService;
import backend.enums.AccountEnum;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mid on 26.12.14.
 */
public class Joystick extends HttpServlet {
    AccountService pool;
    public Joystick(AccountService p) {
        this.pool=p;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(pool.checkLogIn(request)== AccountEnum.UserLoggedIn) {
            response.setStatus(HttpServletResponse.SC_OK);

            //пока без проверки - ты залогинен!

            //т.е. надо быть залогиненым чтоб быть джойстиком
            String name = this.pool.getArraySessionId().get(request.getSession().getId().toString()).getLogin();

            pool.addJoystick(name,request.getSession().getId().toString());//добавляем джойстик в карту джойтиков

        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
