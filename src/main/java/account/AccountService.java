package account;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import database.UserDAO;
import org.mindrot.jbcrypt.BCrypt;


public class AccountService {

    private UserDAO userDAO;
    private Map<Long, User> users;
    private AtomicLong userIdGenerator;

    public AccountService() {
        userDAO = new UserDAO("jdbc:mysql://",
                "localhost:",
                "3306/",
                "game?",
                "user=AlexO&",
                "password=pwd");
        users = new HashMap<>();
        userIdGenerator = new AtomicLong();
    }

    public long userAuthentication(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return -1;
        }

        User user = userDAO.getUserByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            user.setUserId(userIdGenerator.getAndIncrement());
            users.put(user.getUserId(), user);
            return user.getUserId();
        }
        else {
            return -1;
        }
    }

    public boolean isAuthorized(Object userIDObj) {
        if (userIDObj == null)
            return false;
        else {
            Long userID = (long)userIDObj;
            return users.containsKey(userID);
        }
    }

    public User getAuthorizeUserByID(Object userIDObj) {
        if (userIDObj == null)
            return null;
        else {
            Long userID = (long)userIDObj;
            return users.get(userID);
        }
    }

    public boolean deAuthorizeUserByID(Object userIDObj) {
        if (userIDObj == null)
            return false;
        else {
            Long userID = (long)userIDObj;
            if (!users.isEmpty()) {
                users.remove(userID);
                return true;
            }
            else {
                return false;
            }
        }
    }

    public boolean userRegistration(String login, String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return userDAO.addNewUser(login, hashed);
    }

}
