package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private final String NAME_USER = "root";
    private final String PASSWORD = "root";
    private final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static Util util;
    private Connection connection;

    private Util(){}

    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, NAME_USER, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }

    public static synchronized Util getInstance() {
        if(util == null){
            util = new Util();
        }
        return util;
    }
}
