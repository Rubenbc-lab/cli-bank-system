package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonAccountRepository implements AccountRepository{
    private final File file = new File("accounts.json");
    private final ObjectMapper mapper = new ObjectMapper();
    private Map<String, Account> accounts = new HashMap<>();

    public JsonAccountRepository() {
        if (file.exists()) {
            try {
                this.accounts = mapper.readValue(file, new TypeReference<Map<String, Account>>() {});
            } catch (IOException e) {
                System.out.println("Cannot access the file: " + e.getMessage());
            }
        }
    }

    @Override
    public void save(Account account) {
        if (account != null) {
            accounts.put(account.getIdentifier().toLowerCase(), account);
            try {
                mapper.writeValue(file, accounts);
            } catch (IOException e) {
                System.out.println("Cannot save the account in file: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Account> findByUser(User user) {
        List<Account> userAccounts = new ArrayList<>();
        if (user == null || user.getId() == null) {
            return userAccounts;
        }

        for (Account account : accounts.values()) {
            boolean matchesByUserId = account.getUserId() != null && account.getUserId().equalsIgnoreCase(user.getId());
            boolean matchesByUserObject = account.getUser() != null && user.equals(account.getUser());

            if (matchesByUserId || matchesByUserObject) {
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }

    @Override
    public Account findById(String id) {
        return accounts.get(id.toLowerCase());
    }

    @Override
    public List<Account> findAll() {
        List<Account> allAcc = new ArrayList<>();
        for (Account account : accounts.values()) {
            allAcc.add(account);
        }
        return allAcc;
    }
}
