package ru.job4j.vacancy;

/**
 * Storage of SQL statements.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SQLStatement {
    /**
     * Add vacancy if not exists
     */
    public static final String ADD_UNIQUE = "INSERT INTO vacancies(title, description, post_date, link)"
            + " SELECT ? AS title, ? AS description, ? AS post_date, ? AS link"
            + " WHERE NOT EXISTS ("
            + " SELECT 1 FROM vacancies WHERE link = ?);";

    /**
     * Create table if not exists
     */
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS vacancies("
            + "id serial PRIMARY KEY,"
            + " title varchar(90) NOT NULL,"
            + " description text NOT NULL,"
            + " post_date timestamp NOT NULL,"
            + " link varchar(200) NOT NULL);";
}
