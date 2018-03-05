package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tracker Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class TrackerTest {
    /**
     * Тест метода add()
     */
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.getAll()[0], is(item));
    }

    /**
     * Тест метода delete()
     */
    @Test
    public void whenDeleteItemThenTrackerHasNoSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        String id = tracker.getAll()[0].getId();
        tracker.delete(id);
        Item[] result = tracker.getAll();
        Item[] expected = new Item[0];
        assertThat(result, is(expected));
    }

    /**
     * Тест метода findById() для случая, когда элемент найден
     */
    @Test
    public void whenFindByIdExistingItemThenReturnItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        Item expected = tracker.getAll()[0];
        String id = expected.getId();
        Item result = tracker.findById(id);
        assertThat(result, is(item));
    }

    /**
     * Тест метода findById() для случая, когда элемент отсутствует
     */
    @Test
    public void whenFindByIdUnexistingItemThenReturnNull() {
        Tracker tracker = new Tracker();
        Item item = new Item("test2", "testDescription", 123L);
        tracker.add(item);
        String id = tracker.getAll()[0].getId() + "0";
        Item result = tracker.findById(id);
        assertTrue(result instanceof EmptyItem);
    }

    /**
     * Тест метода replace()
     */
    @Test
    public void whenReplaceItemThenItemIsReplaced() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        Item replace = new Item("test2", "testDescription", 456L);
        tracker.add(item);
        String itemId = tracker.getAll()[0].getId();
        tracker.replace(itemId, replace);
        Item[] expected = {replace};
        assertThat(tracker.getAll(), is(expected));
    }

    /**
     * Тест метода gatAll()
     */
    @Test
    public void whenGetAllItemsThenReturnAllItems() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("test1", "testDescription", 123L);
        Item item2 = new Item("test2", "testDescription", 456L);
        tracker.add(item1);
        tracker.add(item2);
        Item[] expected = {item1, item2};
        Item[] result = tracker.getAll();
        assertThat(result, is(expected));
    }

    /**
     * Тест метода findById() для случая, когда заявка найдена
     */
    @Test
    public void whenFindByNameExistingItemThenReturnItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        Item result = tracker.findByName("test1")[0];
        assertThat(result, is(item));
    }

    /**
     * Тест метода findById() для случая, когда заявка отсутствует
     */
    @Test
    public void whenFindByNameUnexistingItemThenReturnEmptyArray() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        Item[] result = tracker.findByName("test2");
        Item[] expected = new Item[0];
        assertThat(result, is(expected));
    }
}