import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * Created by alexander on 19.02.14.
 */
public class AuthUser {
    public static boolean getUserByLogin(String login, String password) {
        String[] users = new String[]{"admin", "user", "AlexO"};
        return Arrays.asList(users).contains(login);
    }
    public static boolean isAuthentication(HttpSession session) {
        if (session.getAttribute("username") != null && session.getAttribute("password") != null)
            return true;
        else
            return false;
    }
}
