package ru.job4j.cars.dao;

import ru.job4j.cars.models.Gearbox;

import java.util.List;
import java.util.Optional;

/**
 * Hibernate implementation of the GearboxDao interface.
 * @author Vadim Bolokhov
 */
public enum HibernateGearboxDao implements GearboxDao {

    INSTANCE;

    private final HibernateGenericDao<Gearbox> dao = new HibernateGenericDao<>(Gearbox.class);

    HibernateGearboxDao() {
        this.dao.setFactory(HibernateFactory.getFactory());
    }

    @Override
    public List<Gearbox> getAll() {
        return this.dao.getAll();
    }

    @Override
    public Optional<Gearbox> getById(int id) {
        return this.dao.getById(id);
    }

    @Override
    public int add(Gearbox gearbox) {
        return this.dao.add(gearbox);
    }

    @Override
    public void update(Gearbox gearbox) {
        this.dao.update(gearbox);
    }

    @Override
    public void delete(int id) {
        this.dao.delete(id);
    }
}
