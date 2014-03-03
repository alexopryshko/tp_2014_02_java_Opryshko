package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alexander on 03.03.14.
 */
public interface ResultHandler<T> {
    T handle(ResultSet result) throws SQLException;
}
