package ru.job4j.cars.dao;

import ru.job4j.cars.models.Model;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for car model related DB queries.
 * @author Vadim Bolokhov
 */
public interface ModelDao {

    List<Model> getAll();

    Optional<Model> getById(int id);

    int add(Model make);

    void update(Model make);

    void delete(int id);

    List<Model> getByMake(int id);
}
