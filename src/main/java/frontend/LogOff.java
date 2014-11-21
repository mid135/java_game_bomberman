package frontend;

/**
 * Created by narek on 27.09.14.
 * updated 300914 mid
 */
import backend.AccountService;
import resources.ResourceFactory;
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
    private Map<String, String> mapMessage;
    private AccountService pool;
    public LogOff(AccountService pool) {
        this.pool = pool;
        mapMessage = ResourceFactory.instance().getResource("./data/LogOff.xml");
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", mapMessage == null ? "" : mapMessage.get("welcome"));
        pool.logOff(request);
        response.getWriter().println(PageGenerator.getPage("authform.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

