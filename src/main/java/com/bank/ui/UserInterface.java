package com.bank.ui;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.service.AccountService;
import com.bank.service.UserService;

import java.util.Scanner;

public class UserInterface {
    private final AccountService accountService;
    private final UserService userService;
    private final Scanner scanner;
    private User currentUser;

    public UserInterface(AccountService accountService, UserService userService, Scanner scanner) {
        this.accountService = accountService;
        this.userService = userService;
        this.scanner = scanner;
        this.currentUser = null;
    }

    public void mainMenu() {
        while(currentUser == null) {
            System.out.println("Welcome to the bank!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            String command = scanner.nextLine();
            processCommand(command);
        }
    }

    private void start() {
        System.out.println("FINE!");
    }
    private void processCommand(String command) {
        switch (command) {
            case "1" -> register();
            case "2" -> login();
            case "3" -> exit();
        }
    }
    private void register() {
        System.out.println("Enter username");
        String username = scanner.nextLine();
        System.out.println("Enter password");
        String password = scanner.nextLine();
        userService.registerUser(username,password);
        if (userService.login(username,password)) {
            this.currentUser = userService.getUserByUsername(username);
            start();
        } else {
            System.out.println("Username not valid");
        }

    }
    private void login() {
        System.out.println("Enter username");
        String username = scanner.nextLine();
        System.out.println("Enter password");
        String password = scanner.nextLine();
        if (userService.login(username,password)) {
            System.out.println("Succesfully logged");
            this.currentUser = userService.getUserByUsername(username);
            start();
        } else {
            System.out.println("Username or password incorrect");
        }
    }
    private void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
