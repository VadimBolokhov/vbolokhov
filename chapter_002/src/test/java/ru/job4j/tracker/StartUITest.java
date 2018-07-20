package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * StartUI Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@Ignore
public class StartUITest {
    private PrintStream stdOut = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private Connection connection;

    /**
     * Замена стандартного вывода на вывод в память
     */
    @Before
    public void loadOut() {
        System.setOut(new PrintStream(this.out));
    }

    @Before
    public void establishConnection() throws SQLException {
        this.connection = new DBConnector().getConnection();
    }

    /**
     * Вывод на консоль
     */
    @After
    public void backOut() {
        System.setOut(this.stdOut);
    }

    /**
     * Тест пункта меню "0. Add new item"
     */
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() throws Exception {
        Input input = new StubInput(Arrays.asList("0", "test name", "desc", "6"));
        try (Tracker tracker = new Tracker(this.connection)) {
            new StartUI(input, tracker).init();
            int lastItemIndex = tracker.getAll().size() - 1;
            Item lastItem = tracker.getAll().get(lastItemIndex);
            tracker.delete(lastItem.getId());
            assertThat(lastItem.getName(), is("test name"));
        }
    }

    /**
     * Тест пункта меню "2. Edit item"
     */
    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            Item item = tracker.add(new Item());
            String itemId = item.getId();
            Input input = new StubInput(Arrays.asList("2", item.getId(), "test name", "desc", "6"));
            new StartUI(input, tracker).init();
            String result = tracker.findById(item.getId()).get().getName();
            tracker.delete(itemId);
            assertThat(result, is("test name"));
        }
    }

    /**
     * Тест пункта меню "3. Delete item"
     */
    @Test
    public void whenDeleteItemThenTrackerHasNoSameItem() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            Item item = tracker.add(new Item());
            String itemId = item.getId();
            Input input = new StubInput(Arrays.asList("3", itemId, "6"));
            new StartUI(input, tracker).init();
            assertThat(tracker.findById(itemId).isPresent(), is(false));
        }
    }

    /**
     * Тест пункта меню "1. Show all items" для случая, когда есть заявки
     */
    @Test
    public void whenShowAllItemsThenOutputStreamHasAllItems() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            String result, expected;
            Item item = new Item("test name", "desc");
            Input input = new StubInput(Arrays.asList("1", "6"));
            tracker.add(item);
            String itemId = item.getId();
            new StartUI(input, tracker).init();
            result = new String(this.out.toByteArray());
            expected = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                    .add("Name: test name")
                    .add("Description: desc")
                    .add("id: " + itemId)
                    .toString();
            tracker.delete(itemId);
            assertTrue(result.contains(expected));
        }
    }

    /**
     * Тест пункта меню "4. Find item by Id", когда заявка найдена
     */
    @Test
    public void whenFindByIdItemThenShowsItemInfoWithSameId() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            String result, expected;
            Item item = new Item("test name", "desc");
            tracker.add(item);
            String itemId = item.getId();
            Input input = new StubInput(Arrays.asList("4", itemId, "6"));
            new StartUI(input, tracker).init();
            result = new String(this.out.toByteArray());
            expected = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                    .add("Name: test name")
                    .add("Description: desc")
                    .add("id: " + itemId)
                    .toString();
            tracker.delete(itemId);
            assertTrue(result.contains(expected));
        }
    }

    /**
     * Тест пункта меню "4. Find item by Id", когда заявка не найдена
     */
    @Test
    public void whenFindByIdUnexistingItemThenShowsMessage() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            String result;
            Item item = new Item("test name", "desc");
            tracker.add(item);
            String itemId = item.getId();
            Input input = new StubInput(Arrays.asList("4", itemId + "999", "6"));
            new StartUI(input, tracker).init();
            result = new String(this.out.toByteArray());
            tracker.delete(itemId);
            assertTrue(result.contains("Заявки не существует"));
        }
    }

    /**
     * Тест пункта меню "5. Find items by name", когда заявка найдена
     */
    @Test
    public void whenFindByNameThenShowsItemsWithSameName() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            String result;
            String expected;
            String name = "findByName test name";
            Item item = new Item(name, "desc");
            tracker.add(item);
            Input input = new StubInput(Arrays.asList("5", name, "6"));
            new StartUI(input, tracker).init();
            result = new String(this.out.toByteArray());
            expected = "Name: " + name;
            tracker.delete(item.getId());
            assertTrue(result.contains(expected));
        }
    }

    /**
     * Тест пункта меню "5. Find items by name", когда заявка не найдена
     */
    @Test
    public void whenFindByNameUnexistingItemThenShowsMessage() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            String result;
            Item item = new Item("test name", "desc");
            tracker.add(item);
            Input input = new StubInput(Arrays.asList("5", "wrong name", "6"));
            new StartUI(input, tracker).init();
            result = new String(this.out.toByteArray());
            tracker.delete(item.getId());
            assertTrue(result.contains("Заявок нет"));
        }
    }
}