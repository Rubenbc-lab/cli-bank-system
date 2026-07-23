package com.bank.service;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.InMemoryAccountRepository;

public class AccountService {
    private final InMemoryAccountRepository accountRepository;

    public AccountService(InMemoryAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public void createAccount(User user) {
        if (user != null) {
            accountRepository.save(new Account(user));
        }
    }
    public void deposit(String id, double amount) {
        if (amount < 0) {
            return;
        }
        Account account = accountRepository.findById(id);
        if (account == null) {
            return;
        }
        account.deposit(amount);
        accountRepository.save(account);
    }
    public void withdraw(String id, double amount) {
        Account account = accountRepository.findById(id);
        if (account == null) {
            return;
        }
        if (amount > 0 && amount <= account.getBalance()) {
            account.withdraw(amount);
            accountRepository.save(account);
        }
    }
    public void transfer(String fromAccountId, String toAccountId, double amount) {
        Account sender = accountRepository.findById(fromAccountId);
        Account receiver = accountRepository.findById(toAccountId);
        if (sender == null || receiver == null) {
            return;
        }
        if (amount > 0 && amount <= sender.getBalance()) {
            receiver.deposit(amount);
            sender.withdraw(amount);
            accountRepository.save(receiver);
            accountRepository.save(sender);
        }
    }
}
