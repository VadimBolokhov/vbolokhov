package ru.job4j.cars.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Hibernate based generic dao.
 * @author Vadim Bolokhov
 */
public class HibernateGenericDao<T> {

    private SessionFactory factory;

    private Class<T> type;

    public HibernateGenericDao(Class<T> type) {
        this.type = type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }

    @SuppressWarnings("unchecked")
    public Optional<T> getById(int id) {
        Optional<T> result;
        try (Session session = this.factory.openSession()) {
            T entity = session.get(this.type, id);
            result = Optional.ofNullable(entity);
        }
        return result;
    }


    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        List<T> result;
        try (Session session = this.factory.openSession()) {
            result = session.createQuery("FROM " + this.type.getName()).list();
        }
        return result;
    }

    public int add(T entity) {
        return this.executeTransaction(
                session -> (int) session.save(entity)
        );
    }

    private <R> R executeTransaction(final Function<Session, R> command) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                R result = command.apply(session);
                transaction.commit();
                return result;
            } catch (HibernateException e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    public boolean update(T entity) {
        return this.executeTransaction(
                session -> {
                    session.update(entity);
                    return true;
                }
        );
    }

    public boolean delete(int id) {
        return this.executeTransaction(
                session -> {
                    boolean deleted = false;
                    T entity = session.get(this.type, id);
                    if (entity != null) {
                        session.delete(entity);
                        deleted = true;
                    }
                    return deleted;
                }
        );
    }

}
