package com.bank;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.InMemoryAccountRepository;
import com.bank.repository.InMemoryUserRepository;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import com.bank.ui.UserInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InMemoryAccountRepository accountRepository = new InMemoryAccountRepository();
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        AccountService accountService = new AccountService(accountRepository);
        UserService userService = new UserService(userRepository);
        UserInterface ui = new UserInterface(accountService,userService,scanner);
        ui.mainMenu();

    }
}
