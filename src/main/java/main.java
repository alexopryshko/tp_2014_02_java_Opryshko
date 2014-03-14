import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ResourceHandler;


public class Main {
    public static void main(String[] args) throws Exception {

        //Create server
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        //create servlet
        context.addServlet(new ServletHolder(new Frontend()), "/*");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        ContextHandler staticContextHandler = new ContextHandler();
        staticContextHandler.setContextPath("/static");
        staticContextHandler.setHandler(resource_handler);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{staticContextHandler, context});
        server.setHandler(handlers);

        server.start();
        server.join();
    }

}
