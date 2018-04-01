package ru.job4j.generic;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

/**
 * SimpleArray Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleArrayTest {
    /**
     * Тест метода add()
     */
    @Test
    public void whenAddItemThenArrayHasSameItem() {
        SimpleArray<Integer> array = new SimpleArray<>(1);
        array.add(10);
        assertThat(array.get(0), is(10));
    }

    /**
     * Тест метода set()
     */
    @Test
    public void whenSetItemThenItemIsReplaced() {
        SimpleArray<Integer> array = new SimpleArray<>(1);
        array.add(10);
        array.set(0, 20);
        assertThat(array.get(0), is(20));
    }

    /**
     * Тест метода delete()
     */
    @Test
    public void whenDeleteItemThenArrayHasNoSameItem() {
        SimpleArray<Integer> array = new SimpleArray<>(1);
        array.add(10);
        array.delete(0);
        assertNull(array.get(0));
    }

    /**
     * Тест итератора
     */
    @Test(expected = NoSuchElementException.class)
    public void whenIteratorReturnsItemsSequentially() {
        SimpleArray<Integer> array = new SimpleArray<>(1);
        array.add(10);
        Iterator<Integer> it = array.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(10));
        assertThat(it.hasNext(), is(false));
        it.next();
    }
}