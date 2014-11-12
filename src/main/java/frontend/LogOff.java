package frontend;

/**
 * Created by narek on 27.09.14.
 * updated 300914 mid
 */

import backend.AccountService;
import backend.enums.AccountEnum;
import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// класс для выхода из нашего акаунта
public class LogOff extends HttpServlet {
    private String message = "Введите логин и пароль для входа";
    private AccountService pool;
    public LogOff(AccountService pool) {
        this.pool=pool;
    }

    private void sendPage(HttpServletResponse response,Map<String, Object> pageVariables, String pageName) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.getPage(pageName, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", message == null ? "" : message);
        pool.logOff(request);
        sendPage(response,pageVariables,"authform.html");

    }

}

