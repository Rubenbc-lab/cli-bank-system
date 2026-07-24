package com.bank.service;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import java.util.List;

/**
 * Service class that handles business logic for the accounts, including account creation,
 * deposit, withdraw or transfer
 */
public class AccountService {
    private final AccountRepository accountRepository;

    /**
     * Creates a constructor that instantiate the account repository given as a parameter
     * @param accountRepository The repository used for persisting data
     */
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Creates a new account with the specified information
     * @param user The user who owns the account
     * @return A new Account object associated to the user, if exists, otherwise return null
     */
    public Account createAccount(User user) {

        if (user != null) {
            Account account = new Account(user);
            accountRepository.save(account);
            return account;
        }
        return null;
    }

    /**
     * Deposit an amount of money to the specified account ID
     * @param id The given ID to look up the account
     * @param amount The amount to deposit
     * @return true if the deposit was successfully, false otherwise
     */
    public boolean deposit(String id, double amount) {
        //Needs a positive amount
        if (amount <= 0) {
            return false;
        }
        // Account must exist
        Account account = accountRepository.findById(id);
        if (account == null) {
            return false;
        }
        account.deposit(amount);
        //Updates repository
        accountRepository.save(account);
        return true;
    }

    /**
     * Withdraws a specify amount of money
     * @param id The account ID from which the money will be withdrawn
     * @param amount The amount of money to withdraw
     * @return true if the operation was completed successfully, false otherwise
     */
    public boolean withdraw(String id, double amount) {
        Account account = accountRepository.findById(id);
        // The account must exist
        if (account == null) {
            return false;
        }
        // The amount must be positive and less or equal than the current balance
        if (amount > 0 && amount <= account.getBalance()) {
            account.withdraw(amount);
            // Updates repository
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    /**
     * Transfer an amount of money from one account to another after validating
     * the account balances
     * @param fromAccountId The id of the sender account
     * @param toAccountId The id of the receiver account
     * @param amount The amount of money to be transferred
     * @return true if the transfer was completed successfully, false otherwise
     */
    public boolean transfer(String fromAccountId, String toAccountId, double amount) {
        Account sender = accountRepository.findById(fromAccountId);
        Account receiver = accountRepository.findById(toAccountId);
        // Sender and receiver accounts must exist
        if (sender == null || receiver == null) {
            return false;
        }
        if (fromAccountId == null || toAccountId == null || fromAccountId.equalsIgnoreCase(toAccountId)) {
            return false;
        }
        // The amount must be positive and less or equal than the current sender balance
        if (amount > 0 && amount <= sender.getBalance()) {
            receiver.deposit(amount);
            sender.withdraw(amount);
            //Updates the repositories
            accountRepository.save(receiver);
            accountRepository.save(sender);

            return true;
        }
        return false;
    }

    /**
     * Retrieves all the accounts associated to a specific user
     * @param user The owner of the accounts
     * @return A List of Account objects belonging to the user
     */
    public List<Account> getAccountsByUser(User user) {
        return accountRepository.findByUser(user);
    }
}
