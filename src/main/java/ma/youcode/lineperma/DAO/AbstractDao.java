package ma.youcode.lineperma.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class AbstractDao<T> implements Dao<T>{
    private static final String DB_URL = "jdbc:sqlite:data/shell.db";

    private static Connection connection;

    protected static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }

        return connection;
    }
}