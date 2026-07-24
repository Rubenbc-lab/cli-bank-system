package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.User;
import java.util.List;

/**
 * Interface defining the persistence operation for Account objects
 * Acts as a contract for data access, allowwing different storage implementations
 */
public interface AccountRepository {
    /**
     * Saves or updates the given account information
     * @param account The account to be saved or updated
     */
    void save(Account account);

    /**
     * Retrieves an account by its ID
     * @param id The given ID to look up
     * @return The match Account object
     */
    Account findById(String id);

    /**
     * Finds all the accounts associated to a specific user
     * @param user The user whose accounts are being retrieved
     * @return A list of Account objects belonging to the specified user
     */
    List<Account> findByUser(User user);

    /**
     * Retrieves all the accounts stored in memory
     * @return A list of Account objects currently stored in memory
     */
    List<Account> findAll();
}
