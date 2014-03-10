import database.ConnectToDB;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;


public class TestAuthUser {

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

    @Test
    public void testIsRegisteredValueNull() {
        Assert.assertFalse(AuthUser.isRegistered("", "", connectToDB.getConnection()));

    }
    @Test
    public void testIsRegisteredExistUser() {
        Assert.assertTrue(AuthUser.isRegistered("admin", "admin", connectToDB.getConnection()));

    }

    public static String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    @Test
    public void testIsRegisteredNotExistUser() {
        Random rnd = new Random();
        for (int i = 0; i < 100; i++) {
            String testString = generateString(rnd, "1234567890aBcDeFg", 10);
            Assert.assertFalse(AuthUser.isRegistered(testString, "NotExist", connectToDB.getConnection()));
        }

    }

    @Test
    public void testRegistrationExistUser() {
        Assert.assertFalse(AuthUser.registration("admin", "admin", connectToDB.getConnection()));
    }

    @Test
    public void testRegistrationNotExistUser() {
        for (int i = 0; i < 5; i++) {
            Assert.assertTrue(AuthUser.registration("testName" + i, "NotExist", connectToDB.getConnection()));
        }
    }

    @After
    public void tearDownGetConnenction() {
        Assert.assertTrue(connectToDB.closeConnection());
    }
}
