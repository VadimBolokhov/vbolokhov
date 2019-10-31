package ru.job4j.cars.service;

import ru.job4j.cars.models.User;

import java.util.List;
import java.util.Optional;

/**
 * User validation service interface.
 * @author Vadim Bolokhov
 */
public interface Validate {
    /**
     * Validate input and add new user to store
     * @param user user to be added
     * @return {@code true} - if user has been added, otherwise - {@code false}
     */
    boolean add(User user);

    /**
     * Validate input and update user info
     * @param user user to be updated
     * @return true - if update succeeds, false - otherwise
     */
    boolean update(User user);

    /**
     * Sets a new password to a user with specified id.
     * @param id user id
     * @param oldPwd user password to be changed
     * @param newPwd user password to be set
     * @return true - if the user exists and old password matches
     */
    boolean setNewPassword(int id, String oldPwd, String newPwd);

    /**
     * Return all users
     * @return user list
     */
    List<User> findAll();

    /**
     * Find user by id
     * @param id user id
     * @return user (if found)
     */
    Optional<User> findById(int id);
}
