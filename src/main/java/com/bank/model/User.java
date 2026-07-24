package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class User {
    private String username;
    private String password;
    private String id;

    @JsonIgnore
    private ArrayList<Account> accounts;



    public User() {
        this.accounts = new ArrayList<>();
    }
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
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getUsername() {
        return this.username;
    }

    public boolean checkPassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }
    public void changePassword(String oldPassword, String newPassword) {
        if (checkPassword(oldPassword)) {
            this.password = newPassword;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
