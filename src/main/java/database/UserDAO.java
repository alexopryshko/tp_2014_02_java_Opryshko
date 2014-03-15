package database;
import account.User;
import java.sql.*;

public class UserDAO {
    private Connection connection;

    public UserDAO(String type, String host, String port, String name, String login, String password) {
        try{
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();
            url.append(type).		    //db type
                append(host). 			//host name
                append(port).			//port
                append(name).    		//db name
                append(login).			//login
                append(password); 		//password
            connection = DriverManager.getConnection(url.toString());

        } catch (SQLException |
                InstantiationException |
                IllegalAccessException |
                ClassNotFoundException e) {
            connection = null;
        }
    }

    public User getUserByUsername(String username) {
        Executor executor = new Executor();
        String password = new String();
        try {
            password = executor.execQuery(connection, "SELECT password FROM users WHERE username='" + username + "';", new ResultHandler<String>() {
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

        if (password != null)
            return new User(0, username, password);
        else {
            return null;
        }
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
