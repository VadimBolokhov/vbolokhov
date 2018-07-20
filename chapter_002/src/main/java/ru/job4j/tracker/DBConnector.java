package ru.job4j.tracker;

import java.sql.*;
import java.util.Properties;

/**
 * Соединение с базой данных.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class DBConnector {

    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/tracker";
        Properties props = this.setProperties();
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, props);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }

    private Properties setProperties() {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        props.setProperty("ssl", "false");
        return props;
    }
}
