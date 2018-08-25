package ru.job4j.vacancy;

import java.sql.*;
import java.util.List;
import java.util.ListIterator;

/**
 * Vacancy depositor.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SQLVacancyDepositor {
    private Connection connection;

    public SQLVacancyDepositor(Connection con) {
        this.connection = con;
    }

    /**
     * Adds unique vacancies to the database
     * @param vacancies list of vacancies to be added
     * @throws SQLException if a database access error occurs
     */
    public void saveVacancies(List<Vacancy> vacancies) throws SQLException {
        createTableIfNotExists();
        addUniqueVacancies(vacancies);
    }

    private void createTableIfNotExists() throws SQLException {
        try (Statement st = this.connection.createStatement()) {
            st.execute(SQLStatement.CREATE_TABLE);
        }
    }

    private void addUniqueVacancies(List<Vacancy> vacancies) throws SQLException {
        ListIterator<Vacancy> li = vacancies.listIterator(vacancies.size());
        while (li.hasPrevious()) {
            this.insertVacancyIfNotExists(li.previous());
        }
    }

    private void insertVacancyIfNotExists(Vacancy current) throws SQLException {
        String currentLink = current.getLink();
        try (PreparedStatement ps = this.connection.prepareStatement(SQLStatement.ADD_UNIQUE)) {
            ps.setString(1, current.getTitle());
            ps.setString(2, current.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(current.getPostDate()));
            ps.setString(4, currentLink);
            ps.setString(5, currentLink);
            ps.executeUpdate();
        }
    }
}
