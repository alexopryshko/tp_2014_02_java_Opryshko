package main;

import account.AccountService;
import frontend.Frontend;
import messageSystem.MessageSystem;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class CreateServer {
    public static Server createServer(Integer PORT) {

        MessageSystem ms = new MessageSystem();

        Frontend frontend = new Frontend(ms);
        AccountService accountService = new AccountService(ms);

        (new Thread(frontend)).start();
        (new Thread(accountService)).start();

        Server server = new Server(PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        ContextHandler staticContextHandler = new ContextHandler();
        staticContextHandler.setContextPath("/static");
        staticContextHandler.setHandler(resource_handler);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{staticContextHandler, context});
        server.setHandler(handlers);

        return server;
    }
}
