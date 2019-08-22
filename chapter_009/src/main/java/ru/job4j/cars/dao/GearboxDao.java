package ru.job4j.cars.dao;

import ru.job4j.cars.models.Gearbox;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for gearbox related DB queries.
 * @author Vadim Bolokhov
 */
public interface GearboxDao {

    List<Gearbox> getAll();

    Optional<Gearbox> getById(int id);

    int add(Gearbox gearbox);

    void update(Gearbox gearbox);

    void delete(int id);
}
