package ru.job4j.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Работа с базой данных SQLite.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class StoreSQL {
    /** Настройки для подключения к базе */
    private Config config;

    StoreSQL(Config config) {
        this.config = config;
    }

    /**
     * Генерирует записи в таблице {@code entry}. Если таблицы нет, то создается и заполняется.
     * @param n максимальное значение для поля {@code field}
     */
    public void generate(int n) throws SQLException {
        try (Connection con = this.config.getConnection()) {
            con.setAutoCommit(false);
            try {
                this.createEntryTable(con);
                this.clearEntryTable(con);
                this.fillEntryTable(con, n);
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    private void createEntryTable(Connection con) throws SQLException {
        String createTable = "CREATE TABLE IF NOT EXISTS entry("
                + "field integer NOT NULL);";
        try (Statement st = con.createStatement()) {
            st.execute(createTable);
        }
    }

    private void clearEntryTable(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.execute("DELETE FROM entry;");
        }
    }

    private void fillEntryTable(Connection con, int n) throws SQLException {
        String insertEntry = "INSERT INTO entry(field) VALUES (?);";
        try (PreparedStatement ps = con.prepareStatement(insertEntry)) {
            for (int i = 1; i <= n; i++) {
                ps.setInt(1, i);
                ps.executeUpdate();
            }
        }
    }

    /**
     * Извлекает все записи из базы
     * @return список записей
     */
    public List<Entry> getAllEntries() throws SQLException {
        List<Entry> result = new ArrayList<>();
        try (Connection con = this.config.getConnection()) {
            String select = "SELECT * FROM entry;";
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(select)) {
                while (rs.next()) {
                    result.add(new Entry(rs.getInt("field")));
                }
            }
        }
        return result;
    }
}
