package ru.job4j.bank;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Bank Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class BankTest {
    private Bank bank;

    @Before
    public void init() {
        this.bank = new Bank();
    }

    /**
     * Тест метода addUser()
     */
    @Test
    public void whenAddUserThenBankHasSameUser() {
        User user = new User("Vadim", "123");
        this.bank.addUser(user);
        Set<User> result = this.bank.getUsers();
        assertTrue(result.contains(user));
    }

    /**
     * Тест метода deleteUser()
     */
    @Test
    public void whenDeleteUserThenBankHasNoSameUser() {
        User user = new User("Vadim", "123");
        this.bank.addUser(user);
        this.bank.deleteUser(user);
        Set<User> result = this.bank.getUsers();
        assertFalse(result.contains(user));
    }

    /**
     * Тест метода addAccountToUser()
     */
    @Test
    public void whenAddAccountToUserThenUserHasSameAccount() {
        this.bank.addUser(new User("Vadim", "123"));
        this.bank.addAccountToUser("123", new Account(1000d, "abc"));
        double result = this.bank.getAccount("123", "abc").get().getValue();
        assertThat(result, is(1000d));
    }

    /**
     * Тест метода deleteAccountFromUser()
     */
    @Test
    public void whenRemoveAccountFromUserThenUserHasNoSameAccount() {
        this.bank.addUser(new User("Vadim", "123"));
        Account acc = new Account(1000d, "abc");
        this.bank.addAccountToUser("123", acc);
        this.bank.deleteAccountFromUser("123", acc);
        List<Account> result = this.bank.getUserAccounts("123");
        assertTrue(result.isEmpty());
    }

    /**
     * Тест метода getUserAccounts()
     */
    @Test
    public void whenGetUserAccountsThenReturnListOfAccounts() {
        this.bank.addUser(new User("Vadim", "123"));
        Account acc = new Account(1000d, "abc");
        this.bank.addAccountToUser("123", acc);
        List<Account> result = this.bank.getUserAccounts("123");
        List<Account> expected = new ArrayList<>();
        expected.add(acc);
        assertThat(result, is(expected));
    }

    /**
     * Тест метода transferMoney(), когда перевод прошел успешно
     */
    @Test
    public void whenTransferMoneySucceeds() {
        this.bank.addUser(new User("Vadim", "123"));
        this.bank.addAccountToUser("123", new Account(1000d, "abc"));
        this.bank.addUser(new User("Vladimir", "456"));
        this.bank.addAccountToUser("456", new Account(0d, "def"));
        assertTrue(this.bank.transferMoney("123", "abc", "456", "def", 500d));
        double source = this.bank.getAccount("123", "abc").get().getValue();
        double dest = this.bank.getAccount("456", "def").get().getValue();
        assertThat(source, is(500d));
        assertThat(dest, is(500d));
    }

    /**
     * Тест метода transferMoney(), года перевод не прошел
     */
    @Test
    public void whenTransferMoneyFails() {
        this.bank.addUser(new User("Vadim", "123"));
        this.bank.addAccountToUser("123", new Account(1000d, "abc"));
        assertFalse(this.bank.transferMoney("123", "abc", "456", "def", 500d));
        double result = this.bank.getAccount("123", "abc").get().getValue();
        assertThat(result, is(1000d));
    }

    /**
     * Тест метода getAccount()
     */
    @Test
    public void whenGetAccount() {
        this.bank.addUser(new User("Vadim", "123"));
        Account acc = new Account(1000d, "abc");
        this.bank.addAccountToUser("123", acc);
        Account result = this.bank.getAccount("123", "abc").get();
        assertThat(result, is(acc));
    }
}