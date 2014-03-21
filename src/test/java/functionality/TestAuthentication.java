package functionality;
import org.junit.Assert;
import org.junit.Test;

public class TestAuthentication extends TestFunctional {
    private final String ip = "http://127.0.0.1:" + PORT;

    @Test
    public void testAuthenticationWithCorrectData() {
        login = "admin";
        password = "admin";
        boolean res = functionalTest(ip, login, password);
        Assert.assertTrue(res);
    }

    @Test
    public void testAuthenticationWithIncorrectData() {
        login = generateString(10);
        password = generateString(10);
        boolean res = functionalTest(ip, login, password);
        Assert.assertFalse(res);
    }
}
