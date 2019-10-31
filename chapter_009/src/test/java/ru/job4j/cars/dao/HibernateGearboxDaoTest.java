package ru.job4j.cars.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Test;
import ru.job4j.cars.models.Gearbox;

import javax.swing.text.html.Option;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for HibernateGearboxDao
 * @author Vadim Bolokhov
 */
public class HibernateGearboxDaoTest {

    private GearboxDao dao = HibernateGearboxDao.INSTANCE;

    private DBConfig config = new DBConfig();

    @After
    public void drop() {
        this.config.dropTable("Gearbox");
    }

    @Test
    public void whenIdIsNotPresentThenShouldReturnEmptyOptional() {
        Optional<Gearbox> result = this.dao.getById(0);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void whenAddGearboxThenStorageShouldContainTheSameEntity() {
        Gearbox box = new Gearbox();
        box.setType("Test");

        int id = this.dao.add(box);
        Optional<Gearbox> result = this.dao.getById(id);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getType(), is("Test"));
    }

    @Test
    public void whenUpdateGearboxThenStorageShouldContainUpdatedEntity() {
        Gearbox box = new Gearbox();
        box.setType("Old");
        int id = this.dao.add(box);

        box.setType("New");
        this.dao.update(box);
        List<Gearbox> result = this.dao.getAll();

        assertThat(result.get(0).getType(), is("New"));
    }

    @Test
    public void whenDeleteGearboxThenShouldReturnEmptyOptional() {
        Gearbox box = new Gearbox();
        box.setType("Test");
        int id = this.dao.add(box);

        this.dao.delete(id);
        Optional<Gearbox> result = this.dao.getById(id);

        assertThat(result.isPresent(), is(false));
    }
}