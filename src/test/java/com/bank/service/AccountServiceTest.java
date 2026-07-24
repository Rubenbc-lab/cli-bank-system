package com.bank.service;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.repository.JsonAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService accountService;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Global arrange
        JsonAccountRepository accountRepository = new JsonAccountRepository();
        accountService = new AccountService(accountRepository);
        testUser = new User("tester_" + System.currentTimeMillis(), "123");
    }

    @Test
    void testCreateAccount() {
        // Arrange & Act
        Account newAccount = accountService.createAccount(testUser);

        // Assert
        assertNotNull(newAccount, "The account created cannot be null (created successfully)");
        assertEquals(0,newAccount.getBalance(), "Account balance should be 0");
    }

    @Test
    void testDepositMoney() {
        // Arrange: Create an account
        Account account = accountService.createAccount(testUser);

        // Act: Deposit +500 balance
        boolean deposit = accountService.deposit(account.getIdentifier(),500);
        boolean depositNegativeAmount = accountService.deposit(account.getIdentifier(), -40);

        // Assert
        assertTrue(deposit, "Deposit must have been made correctly");
        assertEquals(500,account.getBalance(), "Balance must be 500");
        assertFalse(depositNegativeAmount, "A deposit with negative amount must not work");
        assertEquals(500, account.getBalance(), "Account balance must be still 500");
    }

    @Test
    void testWithdrawMoney() {
        // Arrange: Create an account and deposit 200
        Account account = accountService.createAccount(testUser);
        accountService.deposit(account.getIdentifier(), 200.0);

        // Act: Withdraw 50 and 500
        boolean correctWithdraw = accountService.withdraw(account.getIdentifier(), 50);
        boolean incorrectWithdraw = accountService.withdraw(account.getIdentifier(), 500);
        boolean negativeWithdraw = accountService.withdraw(account.getIdentifier(), -50);


        // Assert
        assertTrue(correctWithdraw, "If balance > amount, method must work");
        assertEquals(150, account.getBalance(), "Balance must decrease by 50");
        assertFalse(incorrectWithdraw, "If balance < amount, method must not work");
        assertFalse(negativeWithdraw, "A negative amount must not work");
        assertEquals(150, account.getBalance(), "After all, account balance must be equal");
    }

    @Test
    void testTransferMoney() {
        // Arrange: Create 2 accounts, first deposit 1000
        Account sender = accountService.createAccount(testUser);
        Account receiver = accountService.createAccount(testUser);
        accountService.deposit(sender.getIdentifier(), 1000.0);

        // Act: First transfer 400 to second
        boolean transfer = accountService.transfer(sender.getIdentifier(),receiver.getIdentifier(),400);
        boolean negativeTransfer = accountService.transfer(sender.getIdentifier(), receiver.getIdentifier(), -20);


        // Assert
        assertTrue(transfer, "Transfer must work correctly");
        assertEquals(600,sender.getBalance(), "Sender balance must decrease by 400");
        assertEquals(400, receiver.getBalance(), "Receiver balance must increase by 400");
        assertFalse(negativeTransfer, "A negative amount to transfer must not work");
        assertEquals(600, sender.getBalance(), "Sender balance must be equal");
        assertEquals(400, receiver.getBalance(), "Receiver balance must remain equal");
    }
}
