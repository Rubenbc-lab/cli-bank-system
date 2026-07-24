package com.bank.repository;

import com.bank.model.User;
import java.util.List;

/**
 * Interface defining the persistence operations for User objects
 */
public interface UserRepository {
    /**
     * Updates or saves the given User object
     * @param user The User object that needs to persist
     */
    void save(User user);

    /**
     * Retrieves the user that matches the given ID
     * @param id The ID to look up for
     * @return A User object that mathces the given ID
     */
    User findById(String id);

    /**
     * Retrieves a user by its username
     * @param username The given username to look up
     * @return A User Object that matches the username
     */
    User findByUsername(String username);

    /**
     * Retrieves all the User objects currently stored in memory
     * @return A List of User objects containing all the current users
     */
    List<User> findAll();
}
