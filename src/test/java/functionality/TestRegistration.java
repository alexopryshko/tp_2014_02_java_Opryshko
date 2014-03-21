package functionality;
import org.junit.Assert;
import org.junit.Test;


public class TestRegistration extends TestFunctional{
    private final String ip = "http://127.0.0.1:" + PORT + "/registration";

    @Test
    public void testRegistrationWithCorrectData() {
        login = generateString(10);
        password = generateString(10);
        boolean res = functionalTest(ip, login, password);
        Assert.assertTrue(res);
    }

    @Test
    public void testRegistrationWithIncorrectData() {
        login = "admin";
        password = "admin";
        boolean res = functionalTest(ip, login, password);
        Assert.assertFalse(res);
    }
}
