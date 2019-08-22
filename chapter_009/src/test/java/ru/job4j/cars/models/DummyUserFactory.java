package ru.job4j.cars.models;

import java.util.LinkedList;

/**
 * Dummy user factory for testing.
 * @author Vadim Bolokhov
 */
public class DummyUserFactory {

    public User getUser() {
        return this.getUser("login", "password");
    }

    public User getUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(Role.USER);
        user.setEmail("some@email.com");
        user.setFirstname("Firstname");
        user.setLastname("Lastname");
        user.setCars(new LinkedList<>());
        return user;
    }
}
