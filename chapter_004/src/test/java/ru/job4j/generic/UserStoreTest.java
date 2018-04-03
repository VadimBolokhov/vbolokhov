package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

/**
 * UserStore Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UserStoreTest {
    private UserStore store;

    @Before
    public void init() {
        this.store = new UserStore(1);
    }

    /**
     * Тест метода add()
     */
    @Test
    public void whenAddUserThenStoreHasSameUser() {
        this.store = new UserStore(1);
        User user = new User("123");
        this.store.add(user);
        User result = this.store.findById("123");
        assertThat(result, is(user));
    }

    /**
     * Тест метода replace()
     */
    @Test
    public void whenReplaceUserThenStoreHasNewUser() {
        User newUser = new User("456");
        this.store.add(new User("123"));
        boolean replaced = this.store.replace("123", newUser);
        User result = this.store.findById("456");
        assertThat(result, is(newUser));
        assertTrue(replaced);
    }

    /**
     * Тест метода delete() для RoleStore
     */
    @Test
    public void whenDeleteRoleThenStoreHasNoSameRole() {
        RoleStore roleStore = new RoleStore(1);
        roleStore.add(new Role("123"));
        boolean deleted = roleStore.delete("123");
        assertNull(roleStore.findById("123"));
        assertTrue(deleted);
    }

    /**
     * Тест методов delete() и replace(), когда искомый элемент
     * отсутствует
     */
    @Test
    public void whenUserWithTargetIdNotFound() {
        assertFalse(this.store.delete("123"));
        assertFalse(this.store.replace("123", new User("456")));
    }
}