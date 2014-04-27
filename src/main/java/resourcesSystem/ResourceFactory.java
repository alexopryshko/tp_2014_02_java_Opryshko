package resourcesSystem;


import java.util.HashMap;
import java.util.Map;

public class ResourceFactory {
    private static ResourceFactory singleton;
    private Map<String, Resource> resources = new HashMap<>();

    public static ResourceFactory instance(){
        if(singleton == null){
            singleton = new ResourceFactory();
        }
        return singleton;
    }

    public void addResource(String path, Resource resource) {
        resources.put(path, resource);
    }

    public Resource getResource(String path) {
        return resources.get(path);
    }

    private ResourceFactory() { }

}
