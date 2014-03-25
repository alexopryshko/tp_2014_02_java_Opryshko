package accountService;

import account.User;
import database.SQLUtil;
import database.UserDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;


public class TestUserDAO {
    private UserDAO userDAO;
    private String correctData;
    private String incorrectData;

    @Before
    public void setUp() {
        userDAO = new UserDAO(new SQLUtil());
        correctData = "testName1";
        incorrectData = "IncorrectData";
    }

    @After
    public void clearUp() {
        userDAO.closeConnection();
    }

    @Test
    public void testGetUserByUsernameWithCorrectData() {
        for (Integer i = 0; i < 5; i++) {
            User user = userDAO.getUserByUsername(correctData + i.toString());
            Assert.assertEquals(user.getUsername(), correctData + i.toString());
        }
    }

    @Test
    public void testGetUserByUsernameWithIncorrectData() {
        Assert.assertNull(userDAO.getUserByUsername(incorrectData));
    }

    @Test
    public void testAddNewUserWithIncorrectData() {
        Assert.assertFalse(userDAO.addNewUser(correctData + "1", correctData));
    }

    @Test
    public void testAddNewUserWithCorrectData() {
        Assert.assertTrue(userDAO.addNewUser(correctData + new Date().toString(), correctData));
    }


}
