package ru.job4j.cars.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate configuration.
 * @author Vadim Bolokhov
 */
public class HibernateFactory {

    private static final SessionFactory FACTORY = new Configuration()
            //.configure("cars-hibernate.cfg.xml").buildSessionFactory();
            .configure("cars-annotations.cfg.xml").buildSessionFactory();

    public static SessionFactory getFactory() {
        return FACTORY;
    }
}
