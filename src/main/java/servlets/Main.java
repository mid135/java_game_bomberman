package servlets;

import backend.UserPool;
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

        UserPool pool=new UserPool();//шлобальный пул юзеров и их сессий

        Auth auth = new Auth(pool);
        LogOff logoff = new LogOff(pool);
        Register register=new Register(pool);

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);

        context.addServlet(new ServletHolder(auth), "/authform");
        context.addServlet(new ServletHolder(logoff), "/logoff");
        context.addServlet(new ServletHolder(register),"/registration");

        server.start();
        server.join();
    }
}
