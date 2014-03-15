import database.UserDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestConnectToDB extends Assert {
    static private UserDAO userDAO = null;

    @Before
    public void startGetConnection() {
        userDAO = new UserDAO("jdbc:mysql://",
                "localhost:",
                "3306/",
                "game?",
                "user=AlexO&",
                "password=pwd");
    }

    @After
    public void tearDownGetConnection() {
        Assert.assertTrue(userDAO.closeConnection());
    }

    @Test
    public void testGetConnection() {
        //Assert.assertNotNull(userDAO.getConnection());
    }
}
