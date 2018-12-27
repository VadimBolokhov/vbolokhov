package ru.job4j.crud.models;

import java.util.List;
import java.util.Optional;

/**
 * User store interface.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public interface Store {

    /**
     * Add user to the store
     * @param user user to be added
     */
    User add(User user);

    /**
     * Update user
     * @param id user id
     * @param newUser new user info
     */
    void update(String id, User newUser);

    /**
     * Delete user from the store
     * @param id
     */
    void delete(String id);

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

    /**
     * Create user storage with root
     */
    void initStore();
}
