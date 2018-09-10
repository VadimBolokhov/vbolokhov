package ru.job4j.crud;

import java.util.List;
import java.util.Optional;

/**
 * Validation service.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public enum ValidateService {
    /** Singleton instance */
    INSTANCE;
    /** User store */
    private final Store store = MemoryStore.INSTANCE;
    /** Next id to be generated */
    private int nextId = 1;
    private static final String NOT_EXISTS = "User does not exist.";

    /**
     * Validate input and add new user to store
     * @param user user to be added
     * @return message
     */
    public synchronized String add(User user) {
        String message = "Login is not set.";
        if (user.getLogin() != null) {
            message = addUserIfNotExists(user);
        }
        return message;
    }

    private String addUserIfNotExists(User user) {
        String result = "User already exists.";
        boolean isNewUser = !userExists(user);
        if (isNewUser) {
            result = addNewUser(user);
        }
        return result;
    }

    private boolean userExists(User user) {
        List<User> users = this.store.findAll();
        return users.contains(user);
    }

    private String addNewUser(User user) {
        String newId = this.generateId();
        user.setId(newId);
        this.store.add(user);
        return String.format("New user with id = %s has been added.", newId);
    }

    private String generateId() {
        return String.valueOf(this.nextId++);
    }

    private boolean userIdExists(String id) {
        return this.store.findById(id).isPresent();
    }

    /**
     * Validate input and update user info
     * @param user user to be updated
     * @return message
     */
    public synchronized String update(User user) {
        String message = NOT_EXISTS;
        String id = user.getId();
        if (id != null && this.userIdExists(id)) {
            this.store.update(id, user);
            message = "User has been updated.";
        }
        return message;
    }

    /**
     * Validate input and delete user from the store
     * @param user user to be deleted
     * @return message
     */
    public synchronized String delete(User user) {
        String message = NOT_EXISTS;
        String id = user.getId();
        if (id != null && this.userIdExists(id)) {
            this.store.delete(id);
            message = "User has been deleted.";
        }
        return message;
    }

    /**
     * Return all users
     * @return user list
     */
    public synchronized List<User> findAll() {
        return this.store.findAll();
    }

    /**
     * Find user by id
     * @param id user id
     * @return user (if found)
     */
    public synchronized Optional<User> findById(String id) {
        return this.store.findById(id);
    }
}
