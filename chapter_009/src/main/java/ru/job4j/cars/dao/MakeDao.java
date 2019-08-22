package ru.job4j.cars.dao;

import ru.job4j.cars.models.Make;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for car makes related DB queries.
 * @author Vadim Bolokhov
 */
public interface MakeDao {

    List<Make> getAll();

    Optional<Make> getById(int id);

    int add(Make make);

    void update(Make make);

    void delete(int id);
}
