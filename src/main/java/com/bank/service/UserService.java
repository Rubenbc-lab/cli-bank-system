package com.bank.service;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String password) {
        User existingUser = this.userRepository.findByUsername(username);
        if (existingUser != null) {
            System.out.println("El usuario ya existe");
            return;
        }
        User user = new User(username, password);
        userRepository.save(user);
    }
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("El usuario no existe");
            return false;
        }
        return user.checkPassword(password);
    }
    public User getUserById(String id) {
        return userRepository.findById(id);
    }

}
