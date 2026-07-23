package com.bank.model;

public class Account {
    private User user;
    private String identifier;
    private double balance;

    public Account(User user) {
        this.user = user;
        this.identifier = generateAccountNumber();
        this.balance = 0.0;
    }
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }
    public void withdrawal(double amount) {
        if (amount <= this.balance && amount > 0) {
            this.balance -= amount;
        }
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


    private static long counter= 100000000;
    private static String generateAccountNumber() {
        counter += 1;
        return "ES91-" + counter;
    }
}
