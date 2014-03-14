import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestUser {
    User user = null;
    @Test
    public void testСonstructCase1() throws Exception {
        user = new User();
        Assert.assertEquals(null, user.getUsername());
    }

    @Test
    public void testСonstructCase2() throws Exception {
        user = new User(1, "test", "test");
        Assert.assertEquals("test", user.getUsername());
    }

    @Test
    public void testGetUserId() throws Exception {
        user = new User(1, "test", "test");
        Assert.assertEquals(1, user.getUserId());
    }

    @Test
    public void testGetPassword() throws Exception {
        user = new User(1, "test", "test");
        Assert.assertEquals("test", user.getPassword());
    }

    @Test
    public void testSetPassword() throws Exception {
        user = new User(1, "test", "test");
        user.setPassword("NewPassword");
        Assert.assertEquals("NewPassword", user.getPassword());
    }








}
