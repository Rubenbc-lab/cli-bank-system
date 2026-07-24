package com.bank.repository;

import com.bank.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository implementation for managing the users with JSON
 * Implemented a dual-memory map for O(1) lookups by username or ID, improving speed
 */
public class JsonUserRepository implements UserRepository {
    // The target JSON file where user records are saved
    private final File file = new File("users.json");
    // Jackson mapper's instance
    private final ObjectMapper mapper = new ObjectMapper();
    // Map implemented for in-memory lookups by a given username
    private Map<String, User> usersMap = new HashMap<>();
    // Map implemented for in-memory lookups by a given id
    private Map<String,User> idsMap = new HashMap<>();

    /**
     * Constructs a new JSON repository
     * Reads existing users from JSON file into memory
     */
    public JsonUserRepository() {
        if (file.exists()) {
            try {
                // Reads the JSON file
                this.usersMap = mapper.readValue(file, new TypeReference<Map<String, User>>() {});
                // Implementing the second map to search by a given id
                for (User user : usersMap.values()) {
                    if (user.getId() != null) {
                        this.idsMap.put(user.getId().toLowerCase(), user);
                    }
                }
            } catch (IOException e) {
                System.out.println("Cannot access the file: " + e.getMessage());
            }
        }
    }

    /**
     * Creates or updates a user information into memory and
     * synchronizes with the JSON file
     * @param user The given user to update or create information
     */
    @Override
    public void save(User user) {
        if (user != null && user.getUsername() != null && user.getId() != null) {
            // Index by both username and ID (normalized to lowercase)
            usersMap.put(user.getUsername().toLowerCase(), user);
            idsMap.put(user.getId().toLowerCase(), user);
            try {
                mapper.writeValue(file, usersMap);
            } catch (IOException e) {
                System.out.println("Cannot save the user in the file: " + e.getMessage());
            }
        }
    }

    /**
     * Retrieves a user by the username
     * @param username Given username to look up
     * @return The matching user to be retrieved
     */
    public User findByUsername(String username) {
        if (username == null) return null;
        return usersMap.get(username.toLowerCase());
    }

    /**
     * Retrieves a user by the ID
     * @param id Given ID to look up
     * @return The matching user to be retrieved
     */
    public User findById(String id) {
        if (id == null) return null;
        return idsMap.get(id.toLowerCase());
    }

    /**
     * Retrieves all the users currently stored in the memory
     * @return A List of User objects containing all the users in memory
     */
    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();
        for (User user : usersMap.values())
            allUsers.add(user);
        return allUsers;
    }
}
