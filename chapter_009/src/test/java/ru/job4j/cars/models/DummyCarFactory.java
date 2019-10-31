package ru.job4j.cars.models;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * Dummy car factory for testing.
 * @author Vadim Bolokhov
 */
public class DummyCarFactory {

    public Car getCar() {
        Car car = new Car();
        car.setModel(this.createModel());
        car.setOwner(this.createUser());
        car.setColor(Color.BLACK);
        car.setPrice(10000d);
        car.setPostDate(LocalDateTime.now());
        Gearbox box = new Gearbox("box");
        car.setGearbox(box);
        return car;
    }

    private Model createModel() {
        Model model = new Model("Test");
        model.setMake(new Make("Make"));
        model.setBody(new Body("Style"));
        model.setEngine(new Engine("Engine"));
        model.setTransmission(new Transmission("Transmission"));
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
        return user;
    }
}
