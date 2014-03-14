import org.junit.Assert;
import org.junit.Test;
import java.util.Random;


public class TestAuthUser {

    private AuthUser authUser = new AuthUser();

    @Test
    public void testIsRegisteredValueNull() {
        Assert.assertFalse(authUser.isRegistered("", ""));

    }
    @Test
    public void testIsRegisteredExistUser() {
        Assert.assertTrue(authUser.isRegistered("admin", "admin"));

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
            Assert.assertFalse(authUser.isRegistered(testString, "NotExist"));
        }

    }

    @Test
    public void testRegistrationExistUser() {
        Assert.assertFalse(authUser.registration("admin", "admin"));
    }

    @Test
    public void testRegistrationNotExistUser() {
        for (int i = 0; i < 5; i++) {
            Assert.assertFalse(authUser.registration("testName" + i, "NotExist"));
        }
    }
}
