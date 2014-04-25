package main;

import org.eclipse.jetty.server.Server;
import resourcesSystem.ResourceFactory;
import resourcesSystem.resources.ServerConf;
import static main.CreateServer.createServer;

public class Main {
    public static void main(String[] args) throws Exception {

        /*VFileSystem fileSystem = new VFileSystem("");
        Iterator<String> fileIterator = fileSystem.getIterator("data/");

        while (fileIterator.hasNext()) {
            String path = fileIterator.next();
            if (!fileSystem.isDirectory(path)) {
                ResourceFactory.instance().addResource (
                        path,
                        (Resource) ReadXMLFileSAX.readXML(fileSystem.getAbsolutePath(path))
                );
            }
        }*/

        ResourcesSystem.init("", "data/");

        ServerConf serverConf = (ServerConf) ResourceFactory.instance().getResource("data/serverConf.xml");
        Server server = createServer(serverConf.getPORT());
        server.start();
        server.join();
    }

}
