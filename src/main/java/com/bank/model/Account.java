package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Account {
    @JsonIgnore
    private User user;

    private String identifier;
    private double balance;
    private String userId;

    public Account() {}

    public Account(User user) {
        this.user = user;
        this.identifier = generateAccountNumber();
        this.balance = 0.0;
        this.userId = user.getId();
    }
    public void deposit(double amount) {
        this.balance += amount;
    }
    public void withdraw(double amount) {

        this.balance -= amount;

    }
    public User getUser() {
        return this.user;
    }
    public String getIdentifier() {
        return this.identifier;
    }
    public double getBalance() {
        return this.balance;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setIdentifier(String id) {
        this.identifier = id;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }


    private static String generateAccountNumber() {
        long uniqueNumber = (long) (Math.random() * 899999999L) + 100000000L;
        return "ES91-" + uniqueNumber;
    }
    @Override
    public String toString() {
        return "Account ID: " + getIdentifier() + ", Account balance: " + getBalance();
    }
}
