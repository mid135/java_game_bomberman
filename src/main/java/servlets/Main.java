package servlets;


import backend.GameMechanics;
import backend.Mechanics.GameMechanicsImpl;
import backend.Mechanics.WebSocketServiceImpl;
import backend.WebSocketService;
import backend.test_memory_base.AccountServiceImpMemory;
import frontend.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import backend.AccountService;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * @author v.chibrikov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        AccountService pool=new AccountServiceImpMemory();//глобальный пул юзеров и их сессий, сейчас из памяти все
        WebSocketService service = new WebSocketServiceImpl();
        GameMechanics mechanics = new GameMechanicsImpl(service);

        Auth auth = new Auth(pool);
        LogOff logoff = new LogOff(pool);
        Register register=new Register(pool);


        if (args.length != 1) {
            System.out.append("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.valueOf(portString);
        System.out.append("Starting at port: ").append(portString).append('\n');

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);

        WebSocketHandler wsHandler = new WebSocketHandler()
        {
            @Override
            public void configure(WebSocketServletFactory factory)
            {
                factory.register(WebSocketService.class);
                factory.register(GameMechanics.class);
            }
        };

        ContextHandler webHandler = new ContextHandler();
        webHandler.setContextPath("/api/mechanics");
        webHandler.setHandler(wsHandler);


        context.addServlet(new ServletHolder(auth), "/authform");
        context.addServlet(new ServletHolder(logoff), "/logoff");
        context.addServlet(new ServletHolder(register),"/registration");
        context.addServlet(new ServletHolder(new AdminServlet(pool)), AdminServlet.adminPageURL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context,webHandler});
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
