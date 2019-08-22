package ru.job4j.cars.dao;

import ru.job4j.cars.models.Make;

import java.util.List;
import java.util.Optional;

/**
 * Hibernate implementation of the MakeDao interface.
 * @author Vadim Bolokhov
 */
public enum HibernateMakeDao implements MakeDao {

    INSTANCE;

    private final HibernateGenericDao<Make> dao = new HibernateGenericDao<>(Make.class);

    HibernateMakeDao() {
        this.dao.setFactory(HibernateFactory.getFactory());
    }

    @Override
    public List<Make> getAll() {
        return this.dao.getAll();
    }

    @Override
    public Optional<Make> getById(int id) {
        return this.dao.getById(id);
    }

    @Override
    public int add(Make make) {
        return this.dao.add(make);
    }

    @Override
    public void update(Make make) {
        this.dao.update(make);
    }

    @Override
    public void delete(int id) {
        this.dao.delete(id);
    }
}
