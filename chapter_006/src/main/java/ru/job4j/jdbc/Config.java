package ru.job4j.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Настройки для подключения к базе.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Config {
    /** Имя файла базы данных */
    private String fileName;

    public Config(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return "jdbc:sqlite:" + fileName;
    }

    /**
     * Устанавливает соединение с базой
     * @return соеднинение
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(this.getUrl());
    }
}
