package ru.job4j.cars.controllers;

import ru.job4j.cars.models.User;
import ru.job4j.cars.service.Validate;

import java.util.*;

/**
 * Validate Service Stub.
 * @author Vadim Bolokhov
 */
public enum ValidateServiceStub implements Validate {

    INSTANCE;

    private Map<Integer, User> users = new LinkedHashMap<>();

    private int nextId = 1;

    @Override
    public boolean add(User user) {
        this.users.put(this.nextId, user);
        user.setId(nextId++);
        return true;
    }

    @Override
    public boolean update(User user) {
        return this.users.replace(user.getId(), user) != null;
    }

    @Override
    public boolean setNewPassword(int id, String oldPwd, String newPwd) {
        boolean success = false;
        User user = this.users.get(id);
        if (user.getPassword().equals(oldPwd)) {
            user.setPassword(newPwd);
            success = true;
        }
        return success;
    }

    @Override
    public List<User> findAll() {
        return new LinkedList<>(this.users.values());
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(this.users.get(id));
    }

}
