package ru.job4j.crud.controllers;

import ru.job4j.crud.models.User;
import ru.job4j.crud.models.Validate;

import java.util.*;

/**
 * Validation service stub.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ValidateStub implements Validate {
    private final Map<String, User> store = new HashMap<>();
    private int ids = 0;

    @Override
    public boolean add(User user) {
        String id = String.valueOf(ids++);
        user = new User.Builder().id(id).login(user.getLogin()).password(user.getPassword())
                .name(user.getName()).email(user.getEmail()).role(user.getRole()).build();
        return this.store.putIfAbsent(id, user) == null;
    }

    @Override
    public String update(User user) {
        String id = user.getId();
        this.store.put(id, user);
        return "update user";
    }

    @Override
    public String delete(String id) {
        this.store.remove(id);
        return "delete user";
    }

    public List<User> findAll() {
        return new ArrayList<>(this.store.values());
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(this.store.get(id));
    }
}
