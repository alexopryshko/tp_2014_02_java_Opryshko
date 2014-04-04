package database;
import helper.TimeHelper;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connector connector) {
        try{
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            connection = DriverManager.getConnection(connector.getConnectionString());

        } catch (SQLException |
                InstantiationException |
                IllegalAccessException |
                ClassNotFoundException e) {
            connection = null;
        }
    }

    public boolean isRegistered (String username, String password) {
        Executor executor = new Executor();
        String password_db = null;
        try {
            password_db = executor.execQuery(
                    connection,
                    "SELECT password FROM users WHERE username='" + username +  "';",
                    new ResultHandler<String>()
                    {
                        public String handle(ResultSet result) throws SQLException {
                            if (result.next()) {
                                return result.getString("password");
                            }
                            else {
                                return null;
                            }
                        }
                    });
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return password_db != null && BCrypt.checkpw(password, password_db);
    }

    public Integer getID(String username) {
        Executor executor = new Executor();
        Integer id = 0;
        try {
            id = executor.execQuery(
                    connection,
                    "SELECT id FROM users WHERE username='" + username + "';",
                    new ResultHandler<Integer>()
                    {
                        public Integer handle(ResultSet result) throws SQLException {
                            if (result.next()) {
                                return result.getInt("id");
                            }
                            else {
                                return 0;
                            }
                        }
                    });
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean addNewUser(String username, String hashed) {
        Executor executor = new Executor();
        Boolean temp = true;
        try {
            executor.execUpdate(connection, "INSERT INTO users (username, password,registration) VALUES ('" +
                    username + "','" +
                    hashed + "', NOW());");
        }
        catch (SQLException e) {
            temp = false;
        }
        return temp;
    }

    public boolean closeConnection() {
        try {
            connection.close();
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }


}
