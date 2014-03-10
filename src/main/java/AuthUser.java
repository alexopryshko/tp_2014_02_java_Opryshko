import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import database.ConnectToDB;
import database.Executor;
import database.ResultHandler;
import org.mindrot.jbcrypt.BCrypt;


/**
 * Created by alexander on 19.02.14.
 */
public class AuthUser {

    public static boolean isRegistered(String login, String password, Connection connection) {
        if (login == null || password == null) {
            return false;
        }
        else if (login.isEmpty() || password.isEmpty()) {
            return false;
        }

        Executor executor = new Executor();
        String temp = new String();
        try {
            temp = executor.execQuery(connection, "select password from users where username='" + login + "';", new ResultHandler<String>() {
                public String handle(ResultSet result) throws SQLException {

                    if (result.next()) {
                        return result.getString("password");
                    }
                    else {
                        return "";
                    }
                }
            });
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (temp.equals(""))
            return false;

        return BCrypt.checkpw(password, temp);
    }

    public static boolean isAuthentication(Map<Long, User> users, HttpSession session) {
        return (users.get(session.getAttribute("UserID")) != null);
    }

    public static boolean registration(String login, String password, Connection connection) {

        Executor executor = new Executor();
        Boolean temp = true;

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        try {
            executor.execUpdate(connection, "insert into users (username, password,registration) values ('" +
                        login + "','" +
                        hashed + "', now());");
        }
        catch (SQLException e) {
            temp = false;
        }

        return temp;
    }

}
