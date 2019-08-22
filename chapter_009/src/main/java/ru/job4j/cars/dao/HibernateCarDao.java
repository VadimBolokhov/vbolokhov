package ru.job4j.cars.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.job4j.cars.models.Car;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Hibernate implementation of the CarDao interface
 * @author Vadim Bolokhov
 */
public enum HibernateCarDao implements CarDao {
    /** Singleton instance */
    INSTANCE;
    /** Logger */
    private static final Logger LOG = LogManager.getLogger(HibernateCarDao.class.getName());
    /** Session factory */
    private final SessionFactory factory = HibernateFactory.getFactory();

    private final HibernateGenericDao<Car> dao = new HibernateGenericDao<>(Car.class);

    HibernateCarDao() {
        this.dao.setFactory(this.factory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Car> getAllCars() {
        return this.dao.getAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Car> getUnsoldCars() {
        List<Car> result;
        try (Session session = this.factory.openSession()) {
            result = session.createQuery("From Car where sold = false").list();
        }
        return result;
    }

    @Override
    public Optional<Car> getCarById(int id) {
        return this.dao.getById(id);
    }

    @Override
    public int addCar(Car car) {
        return this.dao.add(car);
    }

    @Override
    public boolean updateCar(Car car) {
        return this.dao.update(car);
    }

    @Override
    public boolean deleteCar(int id) {
        return this.dao.delete(id);
    }
}
