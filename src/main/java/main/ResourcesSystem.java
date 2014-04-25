package main;

import resourcesSystem.ReadXMLFileSAX;
import resourcesSystem.Resource;
import resourcesSystem.ResourceFactory;
import virtualFileSystem.VFileSystem;

import java.util.Iterator;

/**
 * Created by alexander on 25.04.14.
 */
public class ResourcesSystem {
    public static void init(String rootDir, String pathDir) {
        VFileSystem fileSystem = new VFileSystem(rootDir);
        Iterator<String> fileIterator = fileSystem.getIterator(pathDir);

        while (fileIterator.hasNext()) {
            String path = fileIterator.next();
            if (!fileSystem.isDirectory(path)) {
                ResourceFactory.instance().addResource (
                        path,
                        (Resource) ReadXMLFileSAX.readXML(fileSystem.getAbsolutePath(path))
                );
            }
        }
    }
}
