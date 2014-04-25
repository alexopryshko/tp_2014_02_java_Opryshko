package resourcesSystem;

import org.junit.Assert;
import org.junit.Test;
import resourcesSystem.resources.TestObj;

import java.io.File;

/**
 * Created by alexander on 26.04.14.
 */
public class TestResourceSystem {
    private String path = "data/testObj.xml";

    @Test
    public void testGetResource() throws Exception {
        ResourceFactory.instance().addResource(path, (Resource) ReadXMLFileSAX.readXML(path));
        Resource a = ResourceFactory.instance().getResource(path);
        Assert.assertTrue(a != null);
    }

    @Test
    public void testXMLReader() throws Exception {
        Object obj = ReadXMLFileSAX.readXML(path);
        Assert.assertTrue(obj.getClass() == TestObj.class);
        Assert.assertTrue(((TestObj) obj).getTestField().equals("/"));
    }
}
