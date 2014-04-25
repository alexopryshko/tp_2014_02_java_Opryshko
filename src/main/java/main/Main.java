package main;

import org.eclipse.jetty.server.Server;
import resourcesSystem.ReadXMLFileSAX;
import resourcesSystem.Resource;
import resourcesSystem.ResourceFactory;
import resourcesSystem.resources.ServerConf;
import virtualFileSystem.VFileSystem;

import java.util.Iterator;

import static main.CreateServer.createServer;

public class Main {
    public static void main(String[] args) throws Exception {

        VFileSystem fileSystem = new VFileSystem("");
        Iterator<String> fileIterator = fileSystem.getIterator("data/");

        while (fileIterator.hasNext()) {
            String path = fileIterator.next();
            if (!fileSystem.isDirectory(path)) {
                ResourceFactory.instance().addResource(path,
                        (Resource) ReadXMLFileSAX.readXML(fileSystem.getAbsolutePath(path)));
            }
        }

        ServerConf serverConf = (ServerConf) ResourceFactory.instance().getResource("data/serverConf.xml");
        Server server = createServer(serverConf.getPORT());
        server.start();
        server.join();
    }

}
