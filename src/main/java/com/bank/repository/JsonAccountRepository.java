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

/**
 * Repository implementation to manage the accounts entities with JSON file persistence
 */
public class JsonAccountRepository implements AccountRepository{
    // The target JSON file where accounts records are stored
    private final File file = new File("accounts.json");
    // Jackson mapper instance
    private final ObjectMapper mapper = new ObjectMapper();
    // In-memory cache mapping account identifiers with its matching accounts
    private Map<String, Account> accounts = new HashMap<>();

    /**
     * Constructs a new JSON repository
     * Loads existing accounts from the file into memory
     */
    public JsonAccountRepository() {
        if (file.exists()) {
            try {
                // Read JSON file into memory
                this.accounts = mapper.readValue(file, new TypeReference<Map<String, Account>>() {});
            } catch (IOException e) {
                System.out.println("Cannot access the file: " + e.getMessage());
            }
        }
    }

    /**
     * Saves or updates an account in the nemory map and synchronizes with the JSON file
     * The identifier is normalized to lowercase to prevent errors
     * @param account The account to save or update
     */
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

    /**
     * Search for the accounts that owns the user given as a parameter
     * Matches accounts by checking the user's ID
     * @param user The user whose accounts needs to be retrieved
     * @return A List of Account objects associated with the user
     */
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

    /**
     * Retrieves the account that matches the account ID given as a parameter
     * @param id The account ID to look up
     * @return An object Account associated with the ID
     */
    @Override
    public Account findById(String id) {
        return accounts.get(id.toLowerCase());
    }

    /**
     * Retrieves all the accounts in memory
     * @return A List of Account objects containing all the accounts in memory
     */
    @Override
    public List<Account> findAll() {
        List<Account> allAcc = new ArrayList<>();
        for (Account account : accounts.values()) {
            allAcc.add(account);
        }
        return allAcc;
    }
}
