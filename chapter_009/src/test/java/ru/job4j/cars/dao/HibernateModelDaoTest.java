package ru.job4j.cars.dao;

import org.junit.After;
import org.junit.Test;
import ru.job4j.cars.models.*;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for HibernateModelDao.
 * @author Vadim Bolokhov
 */
public class HibernateModelDaoTest {

    private ModelDao dao = HibernateModelDao.INSTANCE;

    private DBConfig config = new DBConfig();

    @After
    public void drop() {
        this.config.truncateSchema();
    }

    @Test
    public void whenAddModelThenStorageShouldContainTheSameEntity() {
        Model model = this.createModel();

        int id = this.dao.add(model);
        Optional<Model> result = this.dao.getById(id);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(model));
    }

    private Model createModel() {
        Model model = new Model("Test");
        model.setMake(new Make("Make"));
        model.setBody(new Body("Style"));
        model.setEngine(new Engine("Engine"));
        model.setTransmission(new Transmission("Transmission"));
        return model;
    }

    @Test
    public void whenGetByMakeIdThenShouldReturnListOfModels() {
        Model model = this.createModel();
        int modelId = this.dao.add(model);
        int makeId = model.getMake().getId();

        List<Model> result = this.dao.getByMake(makeId);

        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0), is(model));
    }

    @Test
    public void whenDeleteModelThenShouldReturnEmptyOptional() {
        Model model = this.createModel();
        int id = this.dao.add(model);

        this.dao.delete(id);
        Optional<Model> result = this.dao.getById(id);

        assertThat(result.isPresent(), is(false));
    }
}