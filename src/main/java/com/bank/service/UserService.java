package com.bank.service;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(String username, String password) {
        User existingUser = this.userRepository.findByUsername(username);
        if (existingUser != null) {
            return false;
        }
        User user = new User(username, password);
        userRepository.save(user);
        return true;
    }
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return user.checkPassword(password);
    }
    public User getUserById(String id) {
        return userRepository.findById(id);
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public void updateRepository(User user) {
        userRepository.save(user);
    }

}
