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

public class JsonUserRepository implements UserRepository {
    private final File file = new File("users.json");
    private final ObjectMapper mapper = new ObjectMapper();
    private Map<String, User> usersMap = new HashMap<>();
    private Map<String,User> idsMap = new HashMap<>();

    public JsonUserRepository() {
        if (file.exists()) {
            try {
                this.usersMap = mapper.readValue(file, new TypeReference<Map<String, User>>() {});
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
    @Override
    public void save(User user) {
        if (user != null && user.getUsername() != null && user.getId() != null) {
            usersMap.put(user.getUsername().toLowerCase(), user);
            idsMap.put(user.getId().toLowerCase(), user);
            try {
                mapper.writeValue(file, usersMap);
            } catch (IOException e) {
                System.out.println("Cannot save the user in the file: " + e.getMessage());
            }
        }
    }
    public User findByUsername(String username) {
        if (username == null) return null;
        return usersMap.get(username.toLowerCase());
    }
    public User findById(String id) {
        if (id == null) return null;
        return idsMap.get(id.toLowerCase());
    }

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();
        for (User user : usersMap.values())
            allUsers.add(user);
        return allUsers;
    }
}
