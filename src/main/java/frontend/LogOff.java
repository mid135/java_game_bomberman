package frontend;

/**
 * Created by narek on 27.09.14.
 * updated 300914 mid
 */
import backend.UserPool;
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

