package ru.job4j.cars.dao;

import ru.job4j.cars.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Hibernate implementation of the UserDao interface.
 * @author Vadim Bolokhov
 */
public enum HibernateUserDao implements UserDao {

    INSTANCE;

    private final HibernateGenericDao<User> dao = new HibernateGenericDao<>(User.class);

    HibernateUserDao() {
        this.dao.setFactory(HibernateFactory.getFactory());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return this.dao.getAll();
    }

    @Override
    public Optional<User> getById(int id) {
        return this.dao.getById(id);
    }

    @Override
    public int add(User user) {
        return this.dao.add(user);
    }

    @Override
    public boolean update(User user) {
        return this.dao.update(user);
    }

    @Override
    public boolean delete(int id) {
        return this.dao.delete(id);
    }
}
