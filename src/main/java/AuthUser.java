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

    public static boolean isRegistered(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            return false;
        }

        Connection connection = ConnectToDB.getConnection();
        Executor executor = new Executor();
        String temp = new String();
        try {
            temp = executor.execQuery(connection, "select password from users where username='" + login + "';", new ResultHandler<String>() {
                public String handle(ResultSet result) throws SQLException {

                    if (result.next()) {
                        return result.getString("password");
                    }
                    else {
                        return BCrypt.hashpw("", BCrypt.gensalt(12));
                    }
                }
            });
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return BCrypt.checkpw(password, temp);
    }

    public static boolean isAuthentication(Map<Long, User> users, HttpSession session) {
        return (users.get(session.getAttribute("UserID")) != null);
    }

    public static boolean registration(String login, String password) {
        Connection connection = ConnectToDB.getConnection();
        Executor executor = new Executor();
        Boolean temp = true;


        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        Integer test = hashed.length();
        System.out.append(test.toString());

        try {
            executor.execUpdate(connection, "insert into users (username, password,registration) values ('" +
                        login + "','" +
                        hashed + "', now());");
        }
        catch (SQLException e) {
            e.printStackTrace();
            temp = false;
        }
        return temp;
    }

}
