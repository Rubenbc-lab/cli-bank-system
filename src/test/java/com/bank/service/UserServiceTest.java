package com.bank.service;

import com.bank.model.User;
import com.bank.repository.JsonUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    private JsonUserRepository userRepository;

    @BeforeEach
    void setUp() {
        //Global arrange. Instantiate userRepository and userService
        userRepository = new JsonUserRepository();
        userService = new UserService(userRepository);
    }

    @Test
    void testRegisterUser() {
        // Arrange
        String username = "testUser_" + System.currentTimeMillis(); // Used unic usernames to avoid fails
        String password = "123";

        // Act
        boolean result = userService.registerUser(username, password);

        // Assert
        assertTrue(result,"Verifying the user has been registered correctly");
        User userRegistered =  userService.getUserByUsername(username);
        assertNotNull(userRegistered, "The registered user cannot be null");
        assertEquals(username,userRegistered.getUsername(), "User username should be equal than the given username");
    }

    @Test
    void testRegisterDuplicateUser() {
        // Arrange: Register an user
        String username = "duplicateUser_" + System.currentTimeMillis();
        userService.registerUser(username, "123");

        // Act: Duplicate the user
        boolean secondAttempt = userService.registerUser(username, "123");

        // Assert
        assertFalse(secondAttempt, "The method should return false, preventing duplicated users");
    }

    @Test
    void testLoginSuccessAndFailure() {
        // Arrange: Preparing users
        String username = "loginUser_" + System.currentTimeMillis();
        userService.registerUser(username, "password123");

        // Act
        boolean correctLogin = userService.login(username,"password123");
        boolean incorrectLogin = userService.login(username, "incorrect");

        // Assert
        assertTrue(correctLogin, "Verifying the user can login with the correct password");
        assertFalse(incorrectLogin, "The user cannot access with incorrect passwords");
    }
}