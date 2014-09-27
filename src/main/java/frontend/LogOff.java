package frontend;

/**
 * Created by narek on 27.09.14.
 */
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
    private String mesage = "Введите логин и пароль для входа";
    private Map<String, Pages> pages;
    private Map<String, User> arraySessionId;

    private Map<String, Boolean> usersStatusAuthorization;
    public LogOff(Map<String, Pages> pages, Map<String, User> arraySessionId, Map<String, Boolean> usersStatusAuthorization) {
        this.pages = pages;
        this.arraySessionId = arraySessionId;

        this.usersStatusAuthorization = usersStatusAuthorization;
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("mesage", mesage == null ? "" : mesage);

        pages.replace(request.getSession().getId(), authform);
        arraySessionId.remove(request.getSession().getId());
        usersStatusAuthorization.replace(request.getSession().getId(), false);

        response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

