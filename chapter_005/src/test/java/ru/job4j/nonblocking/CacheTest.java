package ru.job4j.nonblocking;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Cache Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class CacheTest {
    @Test
    public void whenAddModelThenCacheHasSameModel() {
        Cache c = new Cache();
        Base model = new Base(1);
        c.add(model);
        assertThat(c.findById(1).get(), is(model));
    }

    @Test
    public void whenRemoveModelThenCacheHasNoSameModel() {
        Cache c = new Cache();
        Base model = new Base(1);
        c.add(model);
        c.delete(model);
        assertThat(c.findById(1).isPresent(), is(false));
    }

    @Test
    public void whenUpdateModelThenCacheHasNewModel() {
        Cache c = new Cache();
        Base model = new Base(1);
        Base newModel = new Base(1);
        c.add(model);
        c.update(newModel);
        assertThat(c.findById(1).get().getVersion(), is(model.getVersion() + 1));
        assertThat(c.findById(1).get(), is(newModel));
    }

    @Test(expected = OptimisticException.class)
    public void whenIncorrectVersion() {
        Cache c = new Cache();
        Base model = new Base(1);
        Base newModel = new Base(1);
        newModel.updateVersion();
        c.add(model);
        c.update(newModel);
    }
}