package ru.job4j.cars.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * DB operations for testing.
 * @author Vadim Bolokhov
 */
public class DBConfig {

    private SessionFactory factory = HibernateFactory.getFactory();

    public void dropTable(String table) {
        try (Session session = HibernateFactory.getFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM " + table).executeUpdate();
            transaction.commit();
        }
    }

    public void truncateSchema() {
        try (Session session = HibernateFactory.getFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE SCHEMA PUBLIC AND COMMIT").executeUpdate();
            transaction.commit();
        }
    }
}
