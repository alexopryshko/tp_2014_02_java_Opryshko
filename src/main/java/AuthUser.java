import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexander on 19.02.14.
 */
public class AuthUser {
    public static boolean isRegistered(String login, String password) {
        Map<String, String> constUser = new HashMap<>();
        constUser.put("admin", "admin");
        constUser.put("user", "123");
        constUser.put("AlexO", "5");

        String temp = constUser.get(login);
        if (temp != null && temp.equals(password))
            return true;
        else
            return false;
    }
    public static boolean isAuthentication(Map<Long, User> users, HttpSession session) {
        if (users.get(session.getAttribute("UserID")) != null)
            return true;
        else
            return false;
    }
}
