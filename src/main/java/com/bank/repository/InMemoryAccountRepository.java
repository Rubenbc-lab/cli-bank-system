package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<String,Account> accounts = new HashMap<>();

    @Override
    public void save(Account account) {
        if (account != null) {
            accounts.put(account.getIdentifier(),account);
        }
    }
    @Override
    public Account findById(String id) {
        return accounts.get(id);
    }

    @Override
    public List<Account> findByUser(User user) {
        List<Account> userAccounts = new ArrayList<>();
        for (Account account : this.accounts.values()) {
            if (account.getUser().equals(user)) {
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }
    public List<Account> findAll() {
        List<Account> allAccounts = new ArrayList<>();
        for (Account account : accounts.values()) {
            allAccounts.add(account);
        }
        return allAccounts;
    }
}
