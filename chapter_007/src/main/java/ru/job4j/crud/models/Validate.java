package ru.job4j.crud.models;

import java.util.List;
import java.util.Optional;

/**
 * Validation interface.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
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
     * @return message
     */
    String update(User user);

    /**
     * Validate input and delete user from the store
     * @param id user to be deleted
     * @return message
     */
    String delete(String id);

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
    Optional<User> findById(String id);
}
