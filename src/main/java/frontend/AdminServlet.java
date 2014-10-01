package frontend;

import backend.UserPool;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mid on 01.10.14.
 */
public class AdminServlet extends HttpServlet {
    public static final String adminPageURL = "/admin";
    private UserPool pool;
    public AdminServlet(UserPool pool) {
        this.pool=pool;
    }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        String timeString = request.getParameter("shutdown");
        if (timeString != null) {
            int timeMS = Integer.valueOf(timeString);
            System.out.print("Server will be down after: "+ timeMS + " ms");
            try{
                Thread.sleep(timeMS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\nShutdown");
            System.exit(0);
        }
        pageVariables.put("serverStatus", "run");
        pageVariables.put("userTotalCount", pool.getUsers().size());
        pageVariables.put("sessionCount", pool.getArraySessionId().size());
        response.getWriter().println(PageGenerator.getPage("admin.html", pageVariables));
    }
}
