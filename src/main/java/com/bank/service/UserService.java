package com.bank.service;

import com.bank.model.User;
import com.bank.repository.UserRepository;

/**
 * Service class that manages the user business logic, including registration, authentication or get usernames and IDs
 */
public class UserService {
    private final UserRepository userRepository;

    /**
     * A constructor implementing the user repository
     * @param userRepository The repository where the information is stored
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user with the given information, if the credentials are valid
     * @param username The username for the new account
     * @param password The password for the new account
     * @return true if the user was successfully registered and saved, false otherwise
     */
    public boolean registerUser(String username, String password) {
        // The username and password must not be null
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            return false;
        }
        // If the user already exists, return false
        User existingUser = this.userRepository.findByUsername(username);
        if (existingUser != null) {
            return false;
        }
        // Otherwise, create a new user and update the repository
        User user = new User(username, password);
        userRepository.save(user);
        return true;
    }

    /**
     * Validates the authentication by cheking their username and password
     * @param username The username of the account attempting to log-in
     * @param password The password of the user
     * @return true if the user was successfully logged, false otherwise
     */
    public boolean login(String username, String password) {
        // The user must exist
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return user.checkPassword(password);
    }

    /**
     * Retrieves the user that matches with the given id
     * @param id The id given to look up
     * @return The user that matches the id
     */
    public User getUserById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieve the user that matches the given username
     * @param username The username to look up
     * @return The user that matches the username
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Updates the user information in the repository
     * @param user The user object containing the information
     */
    public void updateRepository(User user) {
        userRepository.save(user);
    }

}
