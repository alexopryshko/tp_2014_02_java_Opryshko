package account;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import database.SQLUtil;
import database.UserDAO;
import org.mindrot.jbcrypt.BCrypt;


public class AccountService {

    private UserDAO userDAO;
    private Map<Long, User> users;
    private AtomicLong userIdGenerator;

    public AccountService() {
        userDAO = new UserDAO(new SQLUtil());
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

    public boolean isAuthorized(Long userID) {
        return userID != null && users.containsKey(userID);
    }

    public User getAuthorizeUserByID(Long userID) {
        if (userID == null)
            return null;
        else {
            return users.get(userID);
        }
    }

    public boolean deAuthorizeUserByID(Long userID) {
        if (userID == null)
            return false;
        else {
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
