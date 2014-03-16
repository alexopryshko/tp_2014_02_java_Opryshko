package functionality;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by alexander on 16.03.14.
 */
public class TestRegistration extends TestFunctional{
    private String ip;
    private String correctLogin;
    private String correctPassword;
    private String incorrectString;


    public static String generateString(int length){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
            result.append((char) (65 + Math.random() * 25));
        return result.toString();
    }

    @Before
    public void localSetUp() {
        ip = "http://127.0.0.1:" + PORT + "/registration";
        correctLogin = generateString(10);
        correctPassword = generateString(10);
        incorrectString = "admin";
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
    public void testRegistrationWithCorrectData() {
        boolean res = functionalTest(ip, correctLogin, correctPassword);
        Assert.assertTrue(res);
    }

    @Test
    public void testRegistrationWithIncorrectData() {
        boolean res = functionalTest(ip, incorrectString, incorrectString);
        Assert.assertFalse(res);
    }
}
