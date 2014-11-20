package servlets;

import frontend.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import backend.AccountService;
import resources.ResourceFactory;
import resources.classesForResources.NumberPort;
import backend.sql_base.AccountServiceImplSQL;
/**
 * @author v.chibrikov
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        //AccountService pool = new AccoutServiceImplMemory();//глобальный пул юзеров и их сессий, сейчас из памяти все
        AccountService pool = new AccountServiceImplSQL();
        NumberPort port = ResourceFactory.instance().getResource("./data/Port.xml");
        Auth auth = new Auth(pool);
        LogOff logoff = new LogOff(pool);
        Register register=new Register(pool);


        /*if (args.length != 1) {
            System.out.append("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.valueOf(portString);*/
        System.out.append("Starting at port: ").append(port.toString()).append('\n');

        Server server = new Server(port.numberPort);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);

        context.addServlet(new ServletHolder(auth), "/authform");
        context.addServlet(new ServletHolder(logoff), "/logoff");
        context.addServlet(new ServletHolder(register),"/registration");
        context.addServlet(new ServletHolder(new AdminServlet(pool)), AdminServlet.adminPageURL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        server.join();

    }
}
