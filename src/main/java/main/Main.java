package main;

import org.eclipse.jetty.server.Server;
import static main.CreateServer.createServer;

public class Main {
    public static void main(String[] args) throws Exception {
        Integer PORT = 8080;
        Server server = createServer(PORT);
        server.start();
        server.join();
    }

}
