package ru.job4j.tracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Создание таблиц в базе.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class DBInitializer {

    public void initBase(Connection con) {
        try {
            Statement statement = con.createStatement();
            statement.execute(new SQLScript().createItemsTable());
            statement.execute(new SQLScript().createCommentsTable());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
