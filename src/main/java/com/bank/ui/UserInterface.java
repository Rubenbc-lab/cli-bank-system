package com.bank.ui;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.service.AccountService;
import com.bank.service.UserService;

import java.util.List;
import java.util.Scanner;

/**
 * Command user interface class responsible for capturing user input from the console and orchestrating
 * interactions between the user and the application's services
 */
public class UserInterface {
    private final AccountService accountService;
    private final UserService userService;
    private final Scanner scanner;
    private User currentUser;
    private boolean running = true;

    /**
     * Constructor that implements the account service, user service and the scanner class
     * @param accountService Service that manages the accounts information
     * @param userService Service that manages the users information
     * @param scanner Scanner instance used for reading inputs
     */
    public UserInterface(AccountService accountService, UserService userService, Scanner scanner) {
        this.accountService = accountService;
        this.userService = userService;
        this.scanner = scanner;
        this.currentUser = null;
    }

    /**
     * Main control loop that switches between the guest menu (register/login)
     * and the authenticated user session loop
     */
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

    /**
     * Secondary loop displayed when a user is already logged in
     * Render banking operations
     */
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

    /**
     * Routes the raw command string entered by the user to its corresponding UI action
     * @param command The command that must be processed
     */
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

    /**
     * Handles the registration workflow
     * Automatically attempts to log in after a successful registration
     */
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

    /**
     * Prompts for user credentials and authenticates the user into the system
     */
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

    /**
     * Exits the application
     */
    private void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }

    /**
     * Creates a new account associated at the current user logged in
     */
    private void createAccount() {
        Account newAccount = accountService.createAccount(currentUser);
        if (newAccount != null) {
            System.out.println("Account created! Your ID is " + newAccount.getIdentifier());
            currentUser.addAccount(newAccount);
            userService.updateRepository(currentUser);
        } else {
                System.out.println("Account cannot be created");
        }
    }

    /**
     * Prompts for the account ID to deposit the money, and for the amount of money,
     * then deposits the money if possible
     */
    private void depositMoney() {
        System.out.println("Enter account ID");
        String id = scanner.nextLine();
        System.out.println("Amount to deposit?");

        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (accountService.deposit(id, amount)) {
                System.out.println("Operation completed!");
            } else {
                System.out.println("Amount or Account ID invalid");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        }
    }

    /**
     * Handles money withdrawal operation with input validation to prevent format errors
     */
    private void withdrawMoney() {
        System.out.println("Enter account ID");
        String id = scanner.nextLine();
        System.out.println("Amount to withdraw");

        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (accountService.withdraw(id, amount)) {
                System.out.println("Operation completed!");
            } else {
                System.out.println("Amount or Account ID invalid");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        }
    }

    /**
     * Handles the workflow to transfer money between accounts, with input validation
     */
    private void transferMoney() {
        System.out.println("Enter your account ID");
        String sender = scanner.nextLine();
        System.out.println("Enter receiver account ID");
        String receiver = scanner.nextLine();
        System.out.println("Amount to transfer");

        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (accountService.transfer(sender, receiver, amount)) {
                System.out.println("Operation completed!");
            } else {
                System.out.println("Amount or Accounts IDs invalid");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        }
    }

    /**
     * Displays all accounts associated to the current logged-in user
     */
    private void viewAccounts() {
        List<Account> accounts = accountService.getAccountsByUser(currentUser);
        if (accounts.isEmpty()) {
            System.out.println("The user has no accounts");
        } else {
            for (Account account : accounts) {
                System.out.println(account);
            }
        }
        /**
         * Logs out the current user, terminates the session loop, and returns to the
         * guest main menu
         */
    }
    private void logout() {
        System.out.println("Logging out");
        running = false;
        currentUser = null;
    }
}
