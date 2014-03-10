import database.ConnectToDB;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestConnectToDB extends Assert {
    static private ConnectToDB connectToDB = null;

    @Before
    public void startGetConnection() {
        connectToDB = new ConnectToDB("jdbc:mysql://",
                "localhost:",
                "3306/",
                "game?",
                "user=AlexO&",
                "password=pwd");
    }

    @After
    public void tearDownGetConnection() {
        Assert.assertTrue(connectToDB.closeConnection());
    }

    @Test
    public void testGetConnection() {
        Assert.assertNotNull(connectToDB.getConnection());
    }
}
