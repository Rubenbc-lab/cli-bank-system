package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a bank account associated with a specific user.
 * Stores account details such as identifier, balance, and owner reference.
 */
public class Account {

    /**
     * Reference to the account owner.
     * Ignored during JSON serialization/deserialization to prevent circular reference issues.
     */
    @JsonIgnore
    private User user;

    private String identifier;
    private double balance;
    /**
     * Unique identifier for the account owner. Persists in JSON to mantain the
     * correlation between accounts and users
     */
    private String userId;
    // Default constructor required by Jackson for JSON
    public Account() {}

     // Constructor receiving a user, implementing the account id and user id
    public Account(User user) {
        this.user = user;
        this.identifier = generateAccountNumber();
        this.balance = 0.0;
        this.userId = user.getId();
    }
    // Increases the account balance by the given amount
    public void deposit(double amount) {
        this.balance += amount;
    }
    // Decreases the account balance by the given amount
    public void withdraw(double amount) {

        this.balance -= amount;

    }
    // Returns the User user as a parameter
    public User getUser() {
        return this.user;
    }
    // Returns the user id as a parameter
    public String getUserId() {
        return this.userId;
    }
    // Returns the account id as a parameter
    public String getIdentifier() {
        return this.identifier;
    }
    // Returns the account balance as a parameter
    public double getBalance() {
        return this.balance;
    }
    // Sets the user who owns the account
    public void setUser(User user) {
        this.user = user;
    }
    // Sets the user id
    public void setUserId(String id) {
        this.userId = id;
    }
    // Sets the account id
    public void setIdentifier(String id) {
        this.identifier = id;
    }
    // Sets the account balance
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Generates a random 9-digit account number prefixed with "ES91-".
     *
     * @return A formatted unique account number string.
     */
    private static String generateAccountNumber() {
        long uniqueNumber = (long) (Math.random() * 899999999L) + 100000000L;
        return "ES91-" + uniqueNumber;
    }

    /**
     * Returns a string representation of the account
     * @return Formatted string containing the account ID, balance and owner's ID
     */
    @Override
    public String toString() {
        return "Account ID: " + getIdentifier() + ", Account balance: " + getBalance() + ", Account owner ID; " + getUserId();
    }
}
