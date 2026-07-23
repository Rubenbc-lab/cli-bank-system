package com.bank;

import com.bank.model.Account;
import com.bank.model.User;

public class Main {
    public static void main(String[] args) {
        User Ruben = new User("ruben","password");
        Account rubenAccount = new Account(Ruben);
        rubenAccount.deposit(100);
        System.out.println(rubenAccount.getBalance());
        rubenAccount.withdraw(50);
        System.out.println(rubenAccount.getBalance());

    }
}
