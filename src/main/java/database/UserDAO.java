package database;
import org.mindrot.jbcrypt.BCrypt;
import resourcesSystem.ResourceFactory;
import resourcesSystem.resources.SQLQueries;

import java.sql.*;

import static java.lang.String.format;

public class UserDAO {

    public static boolean isRegistered (Connection connection, String username, String password) {
        Executor executor = new Executor();
        String password_db = null;
        try {
            SQLQueries query = (SQLQueries) ResourceFactory.instance().getResource("data/sqlQueries.xml");
            password_db = executor.execQuery(
                    connection,
                    format(query.getSelectUserByUsername(), username),
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

    public static Integer getID(Connection connection, String username) {
        Executor executor = new Executor();
        Integer id = 0;
        try {
            SQLQueries query = (SQLQueries) ResourceFactory.instance().getResource("data/sqlQueries.xml");
            id = executor.execQuery(
                    connection,
                    format(query.getSelectIDByUsername(), username),
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

    public static boolean addNewUser(Connection connection, String username, String hashed) {
        Executor executor = new Executor();
        Boolean temp = true;
        try {
            SQLQueries query = (SQLQueries) ResourceFactory.instance().getResource("data/sqlQueries.xml");
            executor.execUpdate(connection, format(query.getInsertUser(), username, hashed));
        }
        catch (SQLException e) {
            temp = false;
        }
        return temp;
    }

}
