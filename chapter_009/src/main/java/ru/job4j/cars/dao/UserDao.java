package ru.job4j.cars.dao;

import ru.job4j.cars.models.User;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for user related DB queries.
 * @author Vadim Bolokhov
 */
public interface UserDao {
    /**
     * Get the list of users.
     * @return list of users
     */
    List<User> getAll();

    /**
     * Get the user by id.
     * @param id the id
     * @return {@code Optional<User>}
     */
    Optional<User> getById(int id);

    /**
     * Save the user object.
     * @param user object to save
     */
    int add(User user);

    /**
     * Update the user object.
     * @param user object to update
     */
    boolean update(User user);

    /**
     * Delete a user from data storage.
     * @param id id of the user to be deleted
     */
    boolean delete(int id);
}
