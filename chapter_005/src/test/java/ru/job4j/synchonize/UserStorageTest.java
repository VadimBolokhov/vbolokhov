package ru.job4j.synchonize;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * UserStorage Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UserStorageTest {
    @Test
    public void whenAddUserThenStorageHasSameUser() {
        UserStorage store = new UserStorage();
        boolean result = store.add(new User(1, 100));
        assertTrue(result);
        assertThat(store.findById(1).isPresent(), is(true));
    }

    @Test
    public void whenUpdateUserThenStorageHasUpdatedUser() {
        UserStorage store = new UserStorage();
        store.add(new User(1, 100));
        boolean result = store.update(new User(1, 200));
        assertTrue(result);
        assertThat(store.findById(1).get().getAmount(), is(200));
    }

    @Test
    public void whenDeleteUserThenStorageHasNoSameUser() {
        UserStorage store = new UserStorage();
        User user = new User(1, 100);
        store.add(user);
        boolean result = store.delete(user);
        assertTrue(result);
        assertThat(store.findById(1).isPresent(), is(false));
        assertThat(store.delete(user), is(false));
    }

    @Test
    public void whenTransferSomeAmountThenUsersHaveUpdatedAmounts() {
        UserStorage store = new UserStorage();
        store.add(new User(1, 100));
        store.add(new User(2, 0));
        store.transfer(1, 2, 50);
        assertThat(store.findById(1).get().getAmount(), is(50));
        assertThat(store.findById(2).get().getAmount(), is(50));
    }

    @Test
    public void whenTransferOperationIsIncorrectThenReturnFalse() {
        UserStorage store = new UserStorage();
        store.add(new User(1, 100));
        store.add(new User(2, 0));
        assertThat(store.transfer(1, 2, 200), is(false));
        assertThat(store.transfer(1, 3, 50), is(false));
        assertThat(store.transfer(1, 2, -100), is(false));
        assertThat(store.findById(1).get().getAmount(), is(100));
        assertThat(store.findById(2).get().getAmount(), is(0));
    }
}