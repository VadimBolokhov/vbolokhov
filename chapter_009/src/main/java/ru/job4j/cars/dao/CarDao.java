package ru.job4j.cars.dao;

import ru.job4j.cars.models.Car;

import java.util.List;
import java.util.Optional;

/**
 * Basic Data Access Object interface.
 * Provides CRUD operations with {@link Car} objects.
 * @author Vadim Bolokhov
 */
public interface CarDao {

    /**
     * Get the list of cars.
     * @return list of cars
     */
    List<Car> getAllCars();

    /**
     * Rerurns a list of cars marked as unsold.
     * @return list of unsold cars
     */
    List<Car> getUnsoldCars();

    /**
     * Get the car by id.
     * @param id the id
     * @return {@code Optional<Car>}
     */
    Optional<Car> getCarById(int id);

    /**
     * Save the car object.
     * @param car object to save
     */
    int addCar(Car car);

    /**
     * Update the car object.
     * @param car object to update
     */
    boolean updateCar(Car car);

    /**
     * Delete the car from data storage.
     * @param id id of the car to be deleted
     */
    boolean deleteCar(int id);
}
