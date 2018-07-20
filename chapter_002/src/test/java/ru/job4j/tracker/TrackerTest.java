package ru.job4j.tracker;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
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
@Ignore
public class TrackerTest {
    private Connection connection;

    /**
     * Замена стандартного вывода на вывод в память
     */
    @Before
    public void loadOut() {
        try {
            this.connection = new DBConnector().getConnection();
            new DBInitializer().initBase(this.connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            Item item = new Item("test1", "testDescription");
            tracker.add(item);
            List<Item> result = tracker.getAll();
            tracker.delete(item.getId());
            assertThat(result.get(result.size() - 1), is(item));
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void whenFindByIdExistingItemThenReturnItem() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            Item item = new Item("test2", "testDescription");
            tracker.add(item);
            int lastIndex = tracker.getAll().size() - 1;
            Item expected = tracker.getAll().get(lastIndex);
            String id = expected.getId();

            Item result = tracker.findById(id).get();
            tracker.delete(item.getId());

            assertThat(result, is(item));
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void whenFindByIdUnexistingItemThenReturnNull() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            Item item = new Item("test3", "testDescription");
            tracker.add(item);
            int lastIndex = tracker.getAll().size() - 1;
            Item expected = tracker.getAll().get(lastIndex);
            String id = expected.getId();

            Optional<Item> result = tracker.findById(id + "999");
            tracker.delete(item.getId());

            assertThat(result.isPresent(), is(false));
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void whenDeleteItemThenTrackerHasNoSameItem() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            Item item = new Item("test4", "testDescription");
            tracker.add(item);
            int lastIndex = tracker.getAll().size() - 1;
            String id = tracker.getAll().get(lastIndex).getId();
            tracker.delete(id);
            Optional<Item> result = tracker.findById(id);
            assertThat(result.isPresent(), is(false));
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void whenFindByNameExistingItemThenReturnItem() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            Item item = new Item("test1", "testDescription");
            tracker.add(item);
            List<Item> items = tracker.findByName("test1");
            int lastIndex = items.size() - 1;

            Item result = items.get(lastIndex);
            tracker.delete(item.getId());

            assertThat(result, is(item));
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void whenReplaceItemThenItemIsReplaced() throws Exception {
        try (Tracker tracker = new Tracker(this.connection)) {
            Item item = new Item("test1", "testDescription1");
            Item replace = new Item("test2", "testDescription2");
            tracker.add(item);
            int lastIndex = tracker.getAll().size() - 1;
            String itemId = tracker.getAll().get(lastIndex).getId();

            tracker.replace(itemId, replace);
            Item result = tracker.findById(itemId).get();
            tracker.delete(item.getId());

            assertThat(result.getName(), is(replace.getName()));
            assertThat(result.getDesc(), is(replace.getDesc()));
        } catch (Exception e) {
            throw e;
        }
    }
}