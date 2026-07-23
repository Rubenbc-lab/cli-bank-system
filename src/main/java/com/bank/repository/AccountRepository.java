package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.User;
import java.util.List;

public interface AccountRepository {
    void save(Account account);
    Account findById(String id);
    List<Account> findByUser(User user);
    List<Account> findAll();
}
