package resourcesSystem;

import org.junit.Assert;
import org.junit.Test;
import resourcesSystem.resources.TestObj;

public class TestResourceSystem {
    private String path = "data/testObj.xml";

    @Test
    public void testGetResource() throws Exception {
        //path = "data/urls.xml";
        ResourceFactory.instance().addResource(path, (Resource) ReadXMLFileSAX.readXML(path));
        Resource resource = ResourceFactory.instance().getResource(path);
        Assert.assertTrue(resource.getClass() == TestObj.class);
    }

    @Test
    public void testXMLReader() throws Exception {
        Object obj = ReadXMLFileSAX.readXML(path);
        Assert.assertTrue(obj.getClass() == TestObj.class);
        Assert.assertTrue(((TestObj) obj).getTestField().equals("/"));
    }
}
