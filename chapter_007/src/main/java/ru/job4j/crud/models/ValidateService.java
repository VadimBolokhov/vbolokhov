package ru.job4j.crud.models;

import java.util.List;
import java.util.Optional;

/**
 * Validation service.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public enum ValidateService implements Validate {
    /** Singleton instance */
    INSTANCE;
    /** User store */
    private final Store store = DBStore.getInstance();
    /** "User does not exist" message */
    private static final String NOT_EXISTS = "User does not exist.";

    public static Validate getInstance() {
        return INSTANCE;
    }

    @Override
    public void initStore() {
        this.store.initStore();
    }

    @Override
    public synchronized boolean add(User user) {
        return this.addUserIfNotExists(user);
    }

    private boolean addUserIfNotExists(User user) {
        boolean isNewUser = !this.userExists(user);
        if (isNewUser) {
            this.addNewUser(user);
        }
        return isNewUser;
    }

    private boolean userExists(User user) {
        List<User> users = this.store.findAll();
        return users.contains(user);
    }

    private String addNewUser(User user) {
        User newUser = this.store.add(user);
        String newId = newUser.getId();
        return String.format("New user with id = %s has been added.", newId);
    }

    private boolean userIdExists(String id) {
        return this.store.findById(id).isPresent();
    }

    @Override
    public synchronized String update(User user) {
        String message = NOT_EXISTS;
        String id = user.getId();
        if (id != null && this.userIdExists(id)) {
            this.store.update(id, user);
            message = "User has been updated.";
        }
        return message;
    }

    @Override
    public synchronized String delete(String id) {
        String message = NOT_EXISTS;
        if (id != null && this.userIdExists(id)) {
            this.store.delete(id);
            message = "User has been deleted.";
        }
        return message;
    }

    @Override
    public synchronized List<User> findAll() {
        return this.store.findAll();
    }

    @Override
    public synchronized Optional<User> findById(String id) {
        return this.store.findById(id);
    }
}
