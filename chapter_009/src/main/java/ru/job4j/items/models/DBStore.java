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
        Item newItem = new Item(item.getDesc(), item.getCreated(), item.isDone());
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
            int key = (int) session.save(newItem);
            newItem.setId(key);
            session.getTransaction().commit();
            } catch (HibernateException e) {
                LOG.error(e.getMessage());
                transaction.rollback();
            }
        }
        return newItem;
    }

    @Override
    public boolean update(Item item) {
        boolean result = false;
        try (Session session = this.factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query query = session.createQuery("UPDATE Item I SET I.done=:done WHERE I.id=:id");
                query.setParameter("done", item.isDone());
                query.setParameter("id", item.getId());
                query.executeUpdate();
                transaction.commit();
                result = true;
            } catch (HibernateException e) {
                LOG.error(e.getMessage());
                transaction.rollback();
            }
        }
        return result;
    }
}
