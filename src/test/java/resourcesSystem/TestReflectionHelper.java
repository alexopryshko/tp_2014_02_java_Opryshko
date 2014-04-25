package resourcesSystem;


import org.junit.Assert;
import org.junit.Test;
import resourcesSystem.resources.DatabaseConf;


public class TestReflectionHelper {
    @Test
    public void testCreateInstanceGood() throws Exception {
        Object obj = ReflectionHelper.createInstance("resourcesSystem.resources.DatabaseConf");
        boolean created = obj.getClass().equals(DatabaseConf.class);
        Assert.assertTrue(created);
    }

    @Test
    public void testSetFieldValue() throws Exception {
        DatabaseConf url = new DatabaseConf();
        ReflectionHelper.setFieldValue(url, "name", "name");
        boolean set = url.getName().equals("name");
        Assert.assertTrue(set);
    }
}
