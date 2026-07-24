package com.bank.ui;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.service.AccountService;
import com.bank.service.UserService;

import java.util.List;
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
                start();
            }
        }
    }

    private void start() {
        this.running = true;
        while (running) {
            System.out.println("");
            System.out.println("a. Create new bank account");
            System.out.println("b. Deposit money");
            System.out.println("c. Withdraw money");
            System.out.println("d. Transfer money");
            System.out.println("e. View my accounts");
            System.out.println("f. Log out");
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
            case "e" -> viewAccounts();
            case "f" -> logout();
        }
    }
    private void register() {
        System.out.println("Enter username");
        String username = scanner.nextLine();
        System.out.println("Enter password");
        String password = scanner.nextLine();
        if (userService.registerUser(username,password)) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Cannot register the user; username already in use");
        }
        if (userService.login(username,password)) {
            System.out.println("Logging in");
            this.currentUser = userService.getUserByUsername(username);
            System.out.println("Logged successfully!");
        } else {
            System.out.println("A problem has occurred logging in");
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
        if (newAccount != null) {
            System.out.println("Account created! Your ID is " + newAccount.getIdentifier());
        } else {
                System.out.println("Account cannot be created");
        }
    }
    private void depositMoney() {
        System.out.println("Enter account ID");
        String id = scanner.nextLine();
        System.out.println("Amount to deposit?");
        double amount = Double.parseDouble(scanner.nextLine());
        if (accountService.deposit(id,amount)) {
            System.out.println("Operation completed!");
        } else {
            System.out.println("Amount or Account ID invalid");
        }
    }
    private void withdrawMoney() {
        System.out.println("Enter account ID");
        String id = scanner.nextLine();
        System.out.println("Amount to withdraw");
        double amount = Double.parseDouble(scanner.nextLine());
        if (accountService.withdraw(id,amount)) {
            System.out.println("Operation completed!");
        } else {
            System.out.println("Amount or Account ID invalid");
        }
    }
    private void transferMoney() {
        System.out.println("Enter your account ID");
        String sender = scanner.nextLine();
        System.out.println("Enter receiver account ID");
        String receiver = scanner.nextLine();
        System.out.println("Amount to transfer");
        double amount = Double.parseDouble(scanner.nextLine());
        if (accountService.transfer(sender,receiver,amount)) {
            System.out.println("Operation completed!");
        } else {
            System.out.println("Amount or Accounts IDs invalid");
        }
    }
    private void viewAccounts() {
        List<Account> accounts =accountService.getAccountsByUser(currentUser);
        if (accounts.isEmpty()) {
            System.out.println("The user has no accounts");
        } else {
            for (Account account : accounts) {
                System.out.println(account);
            }
        }

    }
    private void logout() {
        System.out.println("Logging out");
        running = false;
        currentUser = null;
    }
}
