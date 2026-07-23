package com.bank.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class User {
    private String username;
    private String password;
    private ArrayList<Account> accounts;
    private String id;


    public User(String username,String password) {
        this.username = username;
        this.password = password;
        this.id = UUID.randomUUID().toString();
        this.accounts = new ArrayList<>();
    }
    public void addAccount(Account account) {
        if (account != null) {
            this.accounts.add(account);
        }
    }
    public void setUsername(String name) {
        this.username = name;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getId() {
        return this.id;
    }
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
    public boolean checkPassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }
    public void setPassword(String oldPassword, String newPassword) {
        if (checkPassword(oldPassword)) {
            this.password = newPassword;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(accounts, user.accounts) && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
