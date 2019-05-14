package ru.job4j.cars.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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
    private final SessionFactory factory =  new Configuration()
            .configure("cars-hibernate.cfg.xml")
            .buildSessionFactory();

    @Override
    @SuppressWarnings("unchecked")
    public List<Car> getAllCars() {
        List<Car> result = new LinkedList<>();
        try (Session session = this.factory.openSession()) {
            result = session.createQuery("FROM Car").list();
        }
        return result;
    }

    @Override
    public Optional<Car> getCarById(int id) {
        Optional<Car> result = Optional.empty();
        try (Session session = this.factory.openSession()) {
            Car car = (Car) session.get(Car.class, id);
            result = Optional.ofNullable(car);
        }
        return result;
    }

    @Override
    public int addCar(Car car) {
        return this.execute(
                session -> (int) session.save(car)
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
                transaction.rollback();
                LOG.error(e.getMessage());
                throw e;
            }
        }
    }

    @Override
    public boolean updateCar(Car car) {
        return this.execute(
                session -> {
                    session.update(car);
                    return true;
                }
        );
    }

    @Override
    public boolean deleteCar(int id) {
        return this.execute(
                session -> {
                    boolean deleted = false;
                    Car car = session.get(Car.class, id);
                    if (car != null) {
                        session.delete(car);
                        deleted = true;
                    }
                    return deleted;
                }
        );
    }
}
