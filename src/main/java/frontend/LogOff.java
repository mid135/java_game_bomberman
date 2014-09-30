package frontend;

/**
 * Created by narek on 27.09.14.
 */
import backend.UserPool;
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
// класс для выхода из нашего акаунта
public class LogOff extends HttpServlet {
    private String message = "Введите логин и пароль для входа";
    private UserPool pool;
    public LogOff(UserPool pool) {
        this.pool=pool;
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", message == null ? "" : message);

        boolean b=pool.logOff(request);


        response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

