package servlets;

import frontend.Frontend;
import frontend.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author v.chibrikov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        //Frontend frontend = new Frontend();
        SignUpServlet frontend = new SignUpServlet();
        LogOff logoff = new LogOff(frontend.getPages(), frontend.getArraySessionId(), frontend.getUsersStatusAuthorization());

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        context.addServlet(new ServletHolder(frontend), "/authform");
        context.addServlet(new ServletHolder(logoff), "/logoff");

        server.start();
        server.join();
    }
}
