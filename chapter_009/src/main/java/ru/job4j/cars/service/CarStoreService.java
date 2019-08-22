package ru.job4j.cars.service;

import ru.job4j.cars.dao.*;
import ru.job4j.cars.models.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * An implementation of CarsService interface.
 * @author Vadim Bolokhov
 */
public enum CarStoreService implements CarsService {

    INSTANCE;

    private final CarDao carDao = HibernateCarDao.INSTANCE;

    private final MakeDao makeDao = HibernateMakeDao.INSTANCE;

    private final ModelDao modelDao = HibernateModelDao.INSTANCE;

    private final GearboxDao gearDao = HibernateGearboxDao.INSTANCE;

    @Override
    public int saveCar(Car car) {
        return this.carDao.addCar(car);
    }

    @Override
    public void update(Car car) {
        this.carDao.updateCar(car);
    }

    @Override
    public List<Car> getAllCars() {
        return this.carDao.getAllCars();
    }

    @Override
    public List<Car> getUnsoldCars() {
        return this.carDao.getUnsoldCars();
    }

    @Override
    public List<Color> getColors() {
        return Arrays.asList(Color.values());
    }

    @Override
    public Optional<Car> getCarById(int id) {
        return this.carDao.getCarById(id);
    }

    @Override
    public List<Make> getMakes() {
        return this.makeDao.getAll();
    }

    @Override
    public List<Model> getModelsByMake(int id) {
        return this.modelDao.getByMake(id);
    }

    @Override
    public List<Gearbox> getGearboxes() {
        return this.gearDao.getAll();
    }

}
