package database;

import java.sql.*;


/**
 * Created by alexander on 03.03.14.
 */
public class Executor {
    public <T> T execQuery(Connection connection, String query, ResultHandler<T> handler)
            throws SQLException {

        Statement stmt = connection.createStatement();

        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result);
        result.close();
        stmt.close();
        return value;
    }

    public void execUpdate(Connection connection, String update) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        stmt.close();
    }
}
