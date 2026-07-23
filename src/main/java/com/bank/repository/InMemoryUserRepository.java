package com.bank.repository;

import com.bank.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String,User> usersNames = new HashMap<>();

    @Override
    public void save(User user) {
        if (user != null) {
            users.put(user.getId(),user);
            usersNames.put(user.getUsername(),user);
        }
    }
    @Override
    public User findById(String id) {
        return users.get(id);
    }
    @Override
    public User findByUsername(String username) {
        return usersNames.get(username);
    }
    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();
        for (User user : users.values()) {
            allUsers.add(user);
        }
        return allUsers;
    }
}
