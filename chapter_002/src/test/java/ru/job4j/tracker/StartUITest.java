package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * StartUI Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class StartUITest {
    private PrintStream stdOut = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private Tracker tracker;

    /**
     * Замена стандартного вывода на вывод в память
     */
    @Before
    public void loadOut() {
        this.tracker = new Tracker();
        System.setOut(new PrintStream(this.out));
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
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Input input = new StubInput(new String[] {"0", "test name", "desc", "6"});
        new StartUI(input, this.tracker).init();
        assertThat(this.tracker.getAll()[0].getName(), is("test name"));
    }

    /**
     * Тест пункта меню "2. Edit item"
     */
    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        Item item = this.tracker.add(new Item());
        Input input = new StubInput(new String[]{"2", item.getId(), "test name", "desc", "6"});
        new StartUI(input, this.tracker).init();
        assertThat(this.tracker.findById(item.getId()).getName(), is("test name"));
    }

    /**
     * Тест пункта меню "3. Delete item"
     */
    @Test
    public void whenDeleteItemThenTrackerHasNoSameItem() {
        Item item = this.tracker.add(new Item());
        Input input = new StubInput(new String[] {"3", item.getId(), "6"});
        new StartUI(input, this.tracker).init();
        assertThat(this.tracker.getAll(), is(new Item[0]));
    }

    /**
     * Тест пункта меню "1. Show all items" для случая, когда есть заявки
     */
    @Test
    public void whenShowAllItemsThenOutputStreamHasAllItems() {
        String result, expected;
        Item item = new Item("test name", "desc");
        Input input = new StubInput(new String[] {"1", "6"});
        this.tracker.add(item);
        new StartUI(input, this.tracker).init();
        result = new String(this.out.toByteArray());
        expected = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("Name: test name")
                .add("Description: desc")
                .add("id: " + item.getId())
                .toString();
        assertTrue(result.contains(expected));
    }

    /**
     * Тест пункта меню "1. Show all items" для случая, когда заявок нет
     */
    @Test
    public void whenNoItems() {
        String result;
        Input input = new StubInput(new String[] {"1", "6"});
        new StartUI(input, this.tracker).init();
        result = new String(this.out.toByteArray());
        assertTrue(result.contains("Заявок нет"));
    }

    /**
     * Тест пункта меню "4. Find item by Id", когда заявка найдена
     */
    @Test
    public void whenFindByIdItemThenShowsItemInfoWithSameId() {
        String result, expected;
        Item item = new Item("test name", "desc");
        this.tracker.add(item);
        Input input = new StubInput(new String[] {"4", item.getId(), "6"});
        new StartUI(input, this.tracker).init();
        result = new String(this.out.toByteArray());
        expected = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("Name: test name")
                .add("Description: desc")
                .add("id: " + item.getId())
                .toString();
        assertTrue(result.contains(expected));
    }

    /**
     * Тест пункта меню "4. Find item by Id", когда заявка не найдена
     */
    @Test
    public void whenFindByIdUnexistingItemThenShowsMessage() {
        String result, expected;
        Item item = new Item("test name", "desc");
        this.tracker.add(item);
        Input input = new StubInput(new String[] {"4", item.getId() + " ", "6"});
        new StartUI(input, this.tracker).init();
        result = new String(this.out.toByteArray());
        expected = new EmptyItem().toString();
        assertTrue(result.contains(expected));
    }

    /**
     * Тест пункта меню "5. Find items by name", когда заявка найдена
     */
    @Test
    public void whenFindByNameThenShowsItemsWithSameName() {
        String result, expected;
        Item item = new Item("test name", "desc");
        this.tracker.add(item);
        Input input = new StubInput(new String[] {"5", "test name", "6"});
        new StartUI(input, this.tracker).init();
        result = new String(this.out.toByteArray());
        expected = "Name: test name";
        assertTrue(result.contains(expected));
    }

    /**
     * Тест пункта меню "5. Find items by name", когда заявка не найдена
     */
    @Test
    public void whenFindByNameUnexistingItemThenShowsMessage() {
        String result;
        Item item = new Item("test name", "desc");
        this.tracker.add(item);
        Input input = new StubInput(new String[]{"5", "wrong name", "6"});
        new StartUI(input, this.tracker).init();
        result = new String(this.out.toByteArray());
        assertTrue(result.contains("Заявок нет"));
    }
}