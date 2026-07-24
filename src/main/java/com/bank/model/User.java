package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a system user/customer within the banking app
 * Manages user authentication details, accounts and IDs
 */
public class User {
    private String username;
    private String password;
    private String id;

    /**
     * List of accounts owned by the user. Ignored by JSON to avoid
     * duplicate information
     */
    @JsonIgnore
    private ArrayList<Account> accounts;

    /**
     * Default user constructor required by Jackson for JSON
     */
    public User() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Constructs a new user with the specified username and password
     * Automatically generates a UUID for the user
     * @param username The username provided by the user for registration and authentication
     * @param password The user's secret password
     */
    public User(String username,String password) {
        this.username = username;
        this.password = password;
        this.id = UUID.randomUUID().toString();
        this.accounts = new ArrayList<>();
    }

    /**
     * Adds a new account for the user
     * @param account New account created by the user
     */
    public void addAccount(Account account) {
        if (account != null) {
            this.accounts.add(account);
        }
    }

    /**
     * Sets the user username
     * @param name The new's username to set
     */
    public void setUsername(String name) {
        this.username = name;
    }

    /**
     * Sets the user password
     * @param password The password given by the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the list of accounts for the user
     * @param accounts The list of accounts to associate
     */
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Sets the user id
     * @param id The id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user's password
     * @return The current password as a String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets the user's id
     * @return The user ID as a String
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the list of accounts the user owns
     * @return The associated list of accounts
     */
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

    /**
     * Gets the current user's username
     * @return The username as a String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Verify if the password given as a parameter matches with the current password
     * @param password The password given by the user
     * @return true if the password match, false otherwise
     */
    public boolean checkPassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

    /**
     * Changes the user's password if the old password verification succeeds
     * @param oldPassword The current password for authentication
     * @param newPassword The password to set
     */
    public void changePassword(String oldPassword, String newPassword) {
        if (checkPassword(oldPassword)) {
            this.password = newPassword;
        }
    }

    /**
     * Compares this user object with another object for equality
     * Two users are considered equals if they share the same id and username
     * @param o   the reference object with which to compare.
     * @return true if the objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    /**
     * Generates a unique hashCode for the User instance based on its ID
     * @return The calculated int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
