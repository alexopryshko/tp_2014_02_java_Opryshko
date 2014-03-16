package functionality;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestAuthentication extends TestFunctional{
    private String ip;
    private String correctLogin;
    private String correctPassword;
    private String randString;


    public static String generateString(int length){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
            result.append((char) (65 + Math.random() * 25));
        return result.toString();
    }

    @Before
    public void localSetUp() {
        ip = "http://127.0.0.1:" + PORT;
        correctLogin = "admin";
        correctPassword = "admin";
        randString = generateString(10);
        try {
            setUp();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @After
    public void localClearUp() {
        try {
            clearUp();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAuthenticationWithCorrectData() {
        boolean res = functionalTest(ip, correctLogin, correctPassword);
        Assert.assertTrue(res);
    }

    @Test
    public void testAuthenticationWithIncorrectData() {
        boolean res = functionalTest(ip, randString, randString);
        Assert.assertFalse(res);
    }
}
