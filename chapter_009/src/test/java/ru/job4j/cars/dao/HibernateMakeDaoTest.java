package ru.job4j.cars.dao;

import org.junit.After;
import org.junit.Test;
import ru.job4j.cars.models.Make;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for HibernateMakeDao.
 * @author Vadim Bolokhov
 */
public class HibernateMakeDaoTest {

    private final MakeDao dao = HibernateMakeDao.INSTANCE;

    private DBConfig config = new DBConfig();

    @After
    public void drop() {
        this.config.dropTable("Make");
    }

    @Test
    public void whenIdIsNotPresentThenShouldReturnEmptyOptional() {
        Optional<Make> makeOpt = this.dao.getById(0);
        assertThat(makeOpt.isPresent(), is(false));
    }

    @Test
    public void whenAddMakeThenStorageHasTheSameMake() {
        Make make = new Make();
        make.setName("Test");

        int id = this.dao.add(make);
        Optional<Make> result = this.dao.getById(id);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getName(), is("Test"));
    }

    @Test
    public void whenUpdateThenShouldReturnUpdatedMake() {
        Make make = new Make();
        make.setName("Old");
        int id = this.dao.add(make);

        make.setName("New");
        this.dao.update(make);
        List<Make> result = this.dao.getAll();

        assertThat(result.get(0).getName(), is("New"));
    }

    @Test
    public void whenDeleteThenShouldReturnEmptyOptional() {
        Make make = new Make();
        make.setName("Test");
        int id = this.dao.add(make);

        this.dao.delete(id);
        Optional<Make> result = this.dao.getById(id);

        assertThat(result.isPresent(), is(false));
    }

}