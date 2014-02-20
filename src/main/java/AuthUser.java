import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by alexander on 19.02.14.
 */
public class AuthUser {
    public static boolean isRegistered(String login, String password) {
        String[] users = new String[]{"admin", "user", "AlexO"};
        return Arrays.asList(users).contains(login);
    }
    public static boolean isAuthentication(Map<Long, User> users, HttpSession session) {
        if (users.get(session.getAttribute("UserID")) != null)
            return true;
        else
            return false;
    }
}
