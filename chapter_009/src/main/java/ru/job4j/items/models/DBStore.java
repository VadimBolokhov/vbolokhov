package ru.job4j.items.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Hibernate processor for persistence layer.
 * @author Vadim Bolokhov
 */
public enum DBStore implements Persistence {

    INSTANCE;
    /** Logger */
    private static final Logger LOG = LogManager.getLogger(DBStore.class.getName());

    private final SessionFactory factory = new Configuration()
            .configure()
            .buildSessionFactory();

    public static Persistence getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Item> getAllItems() {
        return this.performQuery("FROM Item I ORDER BY I.created");
    }

    @Override
    public List<Item> getUndoneItems() {
        return this.performQuery("FROM Item I WHERE I.done=false ORDER BY I.created");
    }

    @SuppressWarnings("unchecked")
    private List<Item> performQuery(String query) {
        List<Item> result = new LinkedList<>();
        try (Session session = this.factory.openSession()) {
            result = session.createQuery(query).list();
        } catch (HibernateException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Item addItem(Item item) {
        return this.execute(
                session -> {
                    Item newItem = new Item(item.getDesc(), item.getCreated(), item.isDone());
                    int key = (int) session.save(newItem);
                    newItem.setId(key);
                    return newItem;
                }
        );
    }

    private <T> T execute(final Function<Session, T> command) {
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                T result = command.apply(session);
                transaction.commit();
                return result;
            } catch (HibernateException e) {
                LOG.error(e.getMessage());
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean update(Item item) {
        return this.execute(session -> {
            Query query = session.createQuery("UPDATE Item I SET I.done=:done WHERE I.id=:id");
            query.setParameter("done", item.isDone());
            query.setParameter("id", item.getId());
            query.executeUpdate();
            return true;
        });
    }
}
