package com.bank.service;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.InMemoryAccountRepository;
import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(User user) {
        if (user != null) {
            Account account = new Account(user);
            accountRepository.save(account);
            return account;
        }
        return null;
    }
    public boolean deposit(String id, double amount) {
        if (amount < 0) {
            return false;
        }
        Account account = accountRepository.findById(id);
        if (account == null) {
            return false;
        }
        account.deposit(amount);
        accountRepository.save(account);
        return true;
    }
    public boolean withdraw(String id, double amount) {
        Account account = accountRepository.findById(id);
        if (account == null) {
            return false;
        }
        if (amount > 0 && amount <= account.getBalance()) {
            account.withdraw(amount);
            accountRepository.save(account);
            return true;
        }
        return false;
    }
    public boolean transfer(String fromAccountId, String toAccountId, double amount) {
        Account sender = accountRepository.findById(fromAccountId);
        Account receiver = accountRepository.findById(toAccountId);
        if (sender == null || receiver == null) {
            return false;
        }
        if (amount > 0 && amount <= sender.getBalance()) {
            receiver.deposit(amount);
            sender.withdraw(amount);
            accountRepository.save(receiver);
            accountRepository.save(sender);

            return true;
        }
        return false;
    }
    public List<Account> getAccountsByUser(User user) {
        return accountRepository.findByUser(user);
    }
}
