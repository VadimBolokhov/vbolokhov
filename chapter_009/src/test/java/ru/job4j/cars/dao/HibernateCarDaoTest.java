package ru.job4j.cars.dao;

import org.junit.Test;
import ru.job4j.cars.models.Body;
import ru.job4j.cars.models.Car;
import ru.job4j.cars.models.Engine;
import ru.job4j.cars.models.Transmission;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * HibernateCarDao Test
 * @author Vadim Bolokhov
 */
public class HibernateCarDaoTest {

    private final CarDao dao = HibernateCarDao.INSTANCE;

    @Test
    public void whenCarWithSpecifiedIdDoesNotExistThenShouldReturnEmptyOptional() {
        Optional<Car> car = this.dao.getCarById(0);
        assertThat(car.isPresent(), is(false));
    }

    @Test
    public void whenAddCarThenStorageHasTheSameCar() {
        Car car = this.createTestCar();

        int id = this.dao.addCar(car);
        Optional<Car> carOptional = this.dao.getCarById(id);
        Car result = carOptional.get();

        assertThat(result, is(car));
    }

    private Car createTestCar() {
        Car car = new Car();
        car.setBody(new Body("Test Body"));
        car.setEngine(new Engine("Test Engine"));
        car.setTransmission(new Transmission("Test Transmission"));
        car.setMake("Audi");
        return car;
    }

    @Test
    public void whenUpdateCarThenStorageHasUpdatedCar() {
        Car car = this.createTestCar();
        int id = this.dao.addCar(car);
        car.setId(id);
        car.setTransmission(new Transmission("updated transmission"));

        this.dao.updateCar(car);
        Car result = this.dao.getCarById(id).get();

        assertThat(result, is(car));
    }

    @Test
    public void whenDeleteCarThenStorageHasNoSameCar() {
        Car car = this.createTestCar();
        int id = this.dao.addCar(car);

        this.dao.deleteCar(id);
        List<Car> result = this.dao.getAllCars();

        assertThat(result.isEmpty(), is(true));
    }
}