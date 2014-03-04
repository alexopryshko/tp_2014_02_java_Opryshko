import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import database.ConnectToDB;
import database.Executor;
import database.ResultHandler;


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
                        return "";
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

        return password.equals(temp);
    }

    public static boolean isAuthentication(Map<Long, User> users, HttpSession session) {
        return (users.get(session.getAttribute("UserID")) != null);
    }

    public static boolean registration(String login, String password) {
        Connection connection = ConnectToDB.getConnection();
        Executor executor = new Executor();
        Boolean temp = false;
        try {
            temp = executor.execQuery(connection, "select password from users where username='" + login + "';", new ResultHandler<Boolean>() {
                public Boolean handle(ResultSet result) throws SQLException {

                    if (!result.next()) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            });
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (temp) {
            try {
                executor.execUpdate(connection, "insert into users (username, password,registration) values ('" +
                        login + "','" +
                        password + "', now());");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return temp;
    }

}
