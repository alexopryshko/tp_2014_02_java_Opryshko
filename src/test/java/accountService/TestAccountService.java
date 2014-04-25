package accountService;
import account.AccountService;
import main.ResourcesSystem;
import messageSystem.MessageSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static functionality.TestHelper.generateString;
import java.util.Date;


public class TestAccountService {
    private AccountService accountService = null;
    private String testString;
    private String correctString;
    private String passwd;

    @Before
    public void setUp() {
        ResourcesSystem.init("", "data/");
        accountService = new AccountService("localhost", 3306, "game_db_test", "root", "");
        int lengthString = 10;
        testString = generateString(lengthString);
        correctString = "admin";
        passwd = "admin";
    }

    @Test
    public void testUserAuthenticationWithCorrectData() {
        Assert.assertTrue(accountService.getUserID(correctString, passwd) != 0);
    }

    @Test
    public void testUserAuthenticationWithIncorrectPwd() {
        Assert.assertFalse(accountService.getUserID(correctString, testString) != 0);
    }

    @Test
    public void testUserAuthenticationWithIncorrectData() {
        Assert.assertFalse(accountService.getUserID(testString, testString) != 0);
    }

    @Test
    public void testUserRegistrationWithCorrectData() {
        Assert.assertTrue(accountService.userRegistration(testString + new Date(), testString));
    }

    @Test
    public void testUserRegistrationWithIncorrectData() {
        Assert.assertFalse(accountService.userRegistration(correctString, passwd));
    }


}
