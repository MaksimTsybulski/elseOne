package finaltask.framework.jdbc;

import finaltask.utils.ConfigFileReader;
import finaltask.utils.Logger;

import java.sql.*;

public class SQLManager {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(ConfigFileReader.getURLJDBS(),
                        ConfigFileReader.getLoginJDBC(), ConfigFileReader.getPasswordJDBS());
            }
            Class.forName("com.mysql.jdbc.Driver");
            if (!connection.isClosed()) {
            }
        } catch (SQLException | ClassNotFoundException t) {
            t.printStackTrace();
            Logger.error("JDBC connection failed");
        }
        return connection;
    }

    public static Statement execute() throws SQLException {
        Connection connection = SQLManager.getConnection();
        if (connection == null) {
            Logger.error("Connection has not been established");
            throw new RuntimeException();
        }
        return connection.createStatement();
    }

    public static ResultSet executeQuery(String sql) throws SQLException {
        return execute().executeQuery(sql);
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            Logger.error("Connection has not been closed");
        }
        Logger.info("Connection has been closed");
    }
}