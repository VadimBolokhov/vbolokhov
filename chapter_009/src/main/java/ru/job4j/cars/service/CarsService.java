package ru.job4j.cars.service;

import ru.job4j.cars.models.*;

import java.util.List;
import java.util.Optional;

/**
 * Cars store service interface.
 * @author Vadim Bolokhov
 */
public interface CarsService {

    int saveCar(Car car);

    void update(Car car);

    List<Car> getAllCars();

    List<Car> getUnsoldCars();

    List<Color> getColors();

    Optional<Car> getCarById(int id);

    List<Make> getMakes();

    List<Model> getModelsByMake(int id);

    List<Gearbox> getGearboxes();
}
