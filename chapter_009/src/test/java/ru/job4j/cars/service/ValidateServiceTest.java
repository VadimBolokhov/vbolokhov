package ru.job4j.cars.service;

import org.junit.After;
import org.junit.Test;
import ru.job4j.cars.dao.DBConfig;
import ru.job4j.cars.models.DummyUserFactory;
import ru.job4j.cars.models.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for ValidateService.
 * @author Vadim Bolokhov
 */
public class ValidateServiceTest {

    private Validate service = ValidateService.INSTANCE;

    private DBConfig config = new DBConfig();

    @After
    public void setUp() {
        this.config.truncateSchema();
    }

    @Test
    public void whenAddUserThenResultShouldContainTheSameUser() {
        User user = new DummyUserFactory().getUser("login", "pwd");

        this.service.add(user);

        User result = this.service.findById(user.getId()).get();
        assertThat(result.getLogin(), is("login"));
    }

    @Test
    public void whenSetNewPasswordThenResultShouldContainUserWithNewPassword() {
        User user = new DummyUserFactory().getUser("login", "old");
        this.service.add(user);

        boolean changed = this.service.setNewPassword(user.getId(), "old", "new");

        User result = this.service.findAll().get(0);
        assertThat(changed, is(true));
        assertThat(result.getPassword(), is("new"));
    }

    @Test
    public void whenSetNewPasswordWithWrongOldOneThenPasswordShouldNotBeChanged() {
        User user = new DummyUserFactory().getUser("login", "old");
        this.service.add(user);

        boolean changed = this.service.setNewPassword(user.getId(), "wrong", "new");

        User result = this.service.findAll().get(0);
        assertThat(changed, is(false));
        assertThat(result.getPassword(), is("old"));
    }

    @Test
    public void whenUpdateUserThenResultShouldContainUserWithUpdatedFields() {
        User user = new DummyUserFactory().getUser();
        this.service.add(user);
        user.setFirstname("firstname0");
        user.setLastname("lastname0");

        this.service.update(user);

        User result = this.service.findAll().get(0);
        assertThat(result.getFirstname(), is("firstname0"));
        assertThat(result.getLastname(), is("lastname0"));
    }
}