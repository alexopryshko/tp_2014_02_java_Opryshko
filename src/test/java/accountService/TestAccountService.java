package accountService;
import account.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;


public class TestAccountService {
    private AccountService accountService = null;
    private String testString;
    private int lengthString;
    private String correctString;
    private String passwd;


    public static String generateString(int length){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
            result.append((char) (65 + Math.random() * 25));
        return result.toString();
    }

    @Before
    public void setUp() {
        accountService = new AccountService();
        testString = generateString(lengthString);
        correctString = "admin";
        passwd = "admin";
        lengthString = 10;
    }

    @Test
    public void testUserAuthenticationWithCorrectData() {
        Assert.assertTrue(accountService.userAuthentication(correctString, passwd) != -1);
    }

    @Test
    public void testUserAuthenticationWithIncorrectData() {
        Assert.assertFalse(accountService.userAuthentication(testString, passwd) != -1);
    }

    @Test
    public void testIsAuthorizedWithAuthorizeUser() {
        long id = accountService.userAuthentication(correctString, passwd);
        Assert.assertTrue(accountService.isAuthorized(id));
    }

    @Test
    public void testIsAuthorizedWithoutAuthorizeUser() {
        Assert.assertFalse(accountService.isAuthorized((long) 0));
    }

    @Test
    public void testGetAuthorizeUserByIDWithAuthorizeUser() {
        long id = accountService.userAuthentication(correctString, passwd);
        Assert.assertNotNull(accountService.getAuthorizeUserByID(id));
    }

    @Test
    public void testGetAuthorizeUserByIDWithoutAuthorizeUser() {
        Assert.assertNull(accountService.getAuthorizeUserByID((long) 0));
    }

    @Test
    public void testDeAuthorizeUserByIDWithAuthorizeUser() {
        long id = accountService.userAuthentication(correctString, passwd);
        Assert.assertTrue(accountService.deAuthorizeUserByID(id));
    }

    @Test
    public void testDeAuthorizeUserByIDWithoutAuthorizeUser() {
        Assert.assertFalse(accountService.deAuthorizeUserByID((long) 0));
    }

    @Test
    public void testUserRegistrationWithCorrectData() {
        Assert.assertTrue(accountService.userRegistration(testString + new Date(), testString));
    }

    @Test
    public void testUserRegistrationWithIncorrectData() {
        Assert.assertFalse(accountService.userRegistration(correctString, correctString));
    }


}
