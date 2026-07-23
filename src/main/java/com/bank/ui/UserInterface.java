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
    private boolean running = true;

    public UserInterface(AccountService accountService, UserService userService, Scanner scanner) {
        this.accountService = accountService;
        this.userService = userService;
        this.scanner = scanner;
        this.currentUser = null;
    }

    public void mainMenu() {
        while(true) {
            if (currentUser == null) {
                System.out.println("Welcome to the bank!");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                String command = scanner.nextLine();
                processCommand(command);
            } else {
                running = true;
                start();
            }
        }
    }

    private void start() {
        while (running) {
            System.out.println("");
            System.out.println("a. Create new bank account");
            System.out.println("b. Deposit money");
            System.out.println("c. Withdraw money");
            System.out.println("d. Transfer money");
            System.out.println("e. Log out");
            String command = scanner.nextLine();
            processCommand(command);
        }
    }
    private void processCommand(String command) {
        switch (command) {
            case "1" -> register();
            case "2" -> login();
            case "3" -> exit();
            case "a" -> createAccount();
            case "b" -> depositMoney();
            case "c" -> withdrawMoney();
            case "d" -> transferMoney();
            case "e" -> logout();
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
            System.out.println("Successfully logged");
            this.currentUser = userService.getUserByUsername(username);
        } else {
            System.out.println("Username or password incorrect");
        }
    }
    private void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
    private void createAccount() {
        Account newAccount = accountService.createAccount(currentUser);
        System.out.println("Account created! Your ID is " + newAccount.getIdentifier());
    }
    private void depositMoney() {
        System.out.println("Enter account ID");
        String id = scanner.nextLine();
        System.out.println("Amount to deposit?");
        double amount = Double.valueOf(scanner.nextLine());
        accountService.deposit(id,amount);
        System.out.println("Operation completed!");
    }
    private void withdrawMoney() {
        System.out.println("Enter account ID");
        String id = scanner.nextLine();
        System.out.println("Amount to withdraw");
        double amount = Double.valueOf(scanner.nextLine());
        accountService.withdraw(id,amount);
        System.out.println("Operation completed!");
    }
    private void transferMoney() {
        System.out.println("Enter your account ID");
        String sender = scanner.nextLine();
        System.out.println("Enter receiver account ID");
        String receiver = scanner.nextLine();
        System.out.println("Amount to transfer");
        double amount = Double.valueOf(scanner.nextLine());
        accountService.transfer(sender,receiver,amount);
        System.out.println("Operation completed!");
    }
    private void logout() {
        System.out.println("Logging out");
        running = false;
        currentUser = null;
    }
}
