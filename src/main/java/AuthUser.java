import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.Map;

import database.ConnectToDB;
import database.Executor;
import database.ResultHandler;
import org.mindrot.jbcrypt.BCrypt;

public class AuthUser {

    private ConnectToDB connectToDB;

    public AuthUser() {
        connectToDB = new ConnectToDB("jdbc:mysql://",
                "localhost:",
                "3306/",
                "game?",
                "user=AlexO&",
                "password=pwd");
    }

    public boolean isRegistered(String login, String password) {
        if (login == null || password == null) {
            return false;
        }
        else if (login.isEmpty() || password.isEmpty()) {
            return false;
        }

        Executor executor = new Executor();
        String temp = "";
        try {
            temp = executor.execQuery(connectToDB.getConnection(), "select password from users where username='" + login + "';", new ResultHandler<String>() {
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

    public boolean isAuthentication(Map<Long, User> users, HttpSession session) {
        Long temp = (Long)session.getAttribute("UserID");
        return (users.get(temp) != null);
    }

    public boolean registration(String login, String password) {

        Executor executor = new Executor();
        Boolean temp = true;

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        try {
            executor.execUpdate(connectToDB.getConnection(), "insert into users (username, password,registration) values ('" +
                        login + "','" +
                        hashed + "', now());");
        }
        catch (SQLException e) {
            temp = false;
        }

        return temp;
    }

}
