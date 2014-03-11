package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
    private Connection connection;

    public ConnectToDB(String type, String host, String port, String name, String login, String password) {
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

    public Connection getConnection() {



        return connection;
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
