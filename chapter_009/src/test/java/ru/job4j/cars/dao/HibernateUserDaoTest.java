package ru.job4j.cars.dao;

import org.junit.After;
import org.junit.Test;
import ru.job4j.cars.models.DummyUserFactory;
import ru.job4j.cars.models.Role;
import ru.job4j.cars.models.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for HibernateUserDao.
 * @author Vadim Bolokhov
 */
public class HibernateUserDaoTest {

    private final UserDao dao = HibernateUserDao.INSTANCE;

    private DBConfig config = new DBConfig();

    @After
    public void drop() {
        this.config.dropTable("User");
    }

    @Test
    public void whenAddNewUserThenStorageShouldContainTheSameEntity() {
        User user = new DummyUserFactory().getUser();

        int id = this.dao.add(user);
        Optional<User> result = this.dao.getById(id);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(user));
    }

    @Test
    public void whenUpdateExistingUserThenStorageShouldContainUpdatedEntity() {
        User user = new DummyUserFactory().getUser();
        int id = this.dao.add(user);

        user.setFirstname("Vadim");
        user.setLastname("Bolokhov");
        this.dao.update(user);
        List<User> users = this.dao.getAll();

        assertThat(users.get(0), is(user));
    }

    @Test
    public void whenDeleteUserThenShouldReturnEmptyOptional() {
        User user = new DummyUserFactory().getUser();
        int id = this.dao.add(user);

        this.dao.delete(id);
        Optional<User> result = this.dao.getById(id);

        assertThat(result.isPresent(), is(false));
    }
}