package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        assertThat(tracker.getAll().get(0), is(item));
    }

    /**
     * Тест метода delete()
     */
    @Test
    public void whenDeleteItemThenTrackerHasNoSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        String id = tracker.getAll().get(0).getId();
        tracker.delete(id);
        List<Item> result = tracker.getAll();
        List<Item> expected = new ArrayList<>();
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
        Item expected = tracker.getAll().get(0);
        String id = expected.getId();
        Item result = tracker.findById(id).get();
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
        String id = tracker.getAll().get(0).getId() + "0";
        Optional<Item> result = tracker.findById(id);
        assertFalse(result.isPresent());
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
        String itemId = tracker.getAll().get(0).getId();
        tracker.replace(itemId, replace);
        assertTrue(tracker.getAll().contains(replace));
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
        List<Item> result = tracker.getAll();
        assertTrue(result.contains(item1));
        assertTrue(result.contains(item2));
    }

    /**
     * Тест метода findById() для случая, когда заявка найдена
     */
    @Test
    public void whenFindByNameExistingItemThenReturnItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        Item result = tracker.findByName("test1").get(0);
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
        List<Item> result = tracker.findByName("test2");
        assertTrue(result.isEmpty());
    }
}