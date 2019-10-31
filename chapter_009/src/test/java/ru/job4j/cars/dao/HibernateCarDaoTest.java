package ru.job4j.cars.dao;

import org.junit.After;
import org.junit.Test;
import ru.job4j.cars.models.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * HibernateCarDao Test
 * @author Vadim Bolokhov
 */
public class HibernateCarDaoTest {

    private CarDao carDao = HibernateCarDao.INSTANCE;

    private UserDao userDao = HibernateUserDao.INSTANCE;

    private ModelDao modelDao = HibernateModelDao.INSTANCE;

    private GearboxDao gearboxDao = HibernateGearboxDao.INSTANCE;

    private DBConfig config = new DBConfig();

    @After
    public void drop() {
        this.config.truncateSchema();
    }

    @Test
    public void whenCarWithSpecifiedIdDoesNotExistThenShouldReturnEmptyOptional() {
        Optional<Car> car = this.carDao.getCarById(0);
        assertThat(car.isPresent(), is(false));
    }

    @Test
    public void whenAddCarThenStorageHasTheSameCar() {
        Car car = this.createTestCar();

        int id = this.carDao.addCar(car);
        Optional<Car> result = this.carDao.getCarById(id);

        assertThat(result.isPresent(), is(true));
    }

    private Car createTestCar() {
        Car car = new Car();
        car.setModel(this.createModel());
        car.setOwner(this.createUser());
        car.setColor(Color.BLACK);
        car.setPrice(10000d);
        car.setPostDate(LocalDateTime.now());
        Gearbox box = new Gearbox("box");
        this.gearboxDao.add(box);
        car.setGearbox(box);
        return car;
    }

    private Model createModel() {
        Model model = new Model("Test");
        model.setMake(new Make("Make"));
        model.setBody(new Body("Style"));
        model.setEngine(new Engine("Engine"));
        model.setTransmission(new Transmission("Transmission"));
        this.modelDao.add(model);
        return model;
    }

    private User createUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        user.setRole(Role.AMDIN);
        user.setEmail("some@email.com");
        user.setFirstname("Firstname");
        user.setLastname("Lastname");
        user.setCars(new LinkedList<>());
        this.userDao.add(user);
        return user;
    }

    @Test
    public void whenUpdateCarThenStorageHasUpdatedCar() {
        Car car = this.createTestCar();
        car.setDesc("Old");
        int id = this.carDao.addCar(car);
        car.setDesc("New");

        this.carDao.updateCar(car);
        Car result = this.carDao.getCarById(id).get();

        assertThat(result.getDesc(), is("New"));
    }

    @Test
    public void whenGetUnsoldCarsThenShouldReturnListOfUnsoldCarsOnly() {
        Car car = this.createTestCar();
        this.carDao.addCar(car);

        List<Car> unsoldCars = this.carDao.getUnsoldCars();

        assertThat(unsoldCars.size(), is(1));
        assertThat(unsoldCars.get(0).isSold(), is(false));
    }

    @Test
    public void whenGetUnsoldCarsButAllSoldThenShouldReturnEmptyList() {
        Car car = this.createTestCar();
        car.setSold(true);
        this.carDao.addCar(car);

        List<Car> result = this.carDao.getUnsoldCars();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void whenDeleteCarThenStorageHasNoSameCar() {
        Car car = this.createTestCar();
        int id = this.carDao.addCar(car);

        this.carDao.deleteCar(id);
        List<Car> result = this.carDao.getAllCars();

        assertThat(result.isEmpty(), is(true));
    }
}