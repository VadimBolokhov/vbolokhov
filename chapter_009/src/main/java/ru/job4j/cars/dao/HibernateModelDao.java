package ru.job4j.cars.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.job4j.cars.models.Make;
import ru.job4j.cars.models.Model;

import java.util.List;
import java.util.Optional;

/**
 * Hibernate implementation of the ModelDao interface.
 * @author Vadim Bolokhov
 */
public enum HibernateModelDao implements ModelDao {

    INSTANCE;

    private final HibernateGenericDao<Model> dao = new HibernateGenericDao<>(Model.class);

    private final SessionFactory factory = HibernateFactory.getFactory();

    HibernateModelDao() {
        this.dao.setFactory(this.factory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Model> getAll() {
        return this.dao.getAll();
    }

    @Override
    public Optional<Model> getById(int id) {
        return this.dao.getById(id);
    }

    @Override
    public int add(Model model) {
        return this.dao.add(model);
    }

    @Override
    public void update(Model model) {
        this.dao.update(model);
    }

    @Override
    public void delete(int id) {
        this.dao.delete(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Model> getByMake(int makeId) {
        List<Model> result;
        try (Session session = this.factory.openSession()) {
            Query query = session.createQuery("From Model where make.id = :makeId");
            query.setParameter("makeId", makeId);
            result = query.list();
        }
        return result;
    }
}
