package com.bank.repository;

import com.bank.model.User;
import java.util.List;

public interface UserRepository {
    void save(User user);
    User findById(String id);
    User findByUsername(String username);
    List<User> findAll();
}
