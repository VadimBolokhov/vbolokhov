package ru.job4j.map;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * SimpleMap Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleMapTest {
    @Test
    public void whenInsertItemThenMapHasSameItem() {
        SimpleMap<Integer, String> map = new SimpleMap<>();
        map.insert(1, "One");
        assertThat(map.get(1), is("One"));
    }

    @Test
    public void whenKeyAlreadyExistsThenShouldReturnFalse() {
        SimpleMap<Integer, String> map = new SimpleMap<>();
        map.insert(1, "One");
        assertThat(map.insert(1, "Test"), is(false));
    }

    @Test
    public void whenDeleteValueByKeyThenMapHasNoSameValue() {
        SimpleMap<Integer, String> map = new SimpleMap<>(0);
        map.insert(1, "One");
        map.delete(1);
        assertNull(map.get(1));
    }

    @Test
    public void whenMapHasOneItemThenIteratorReturnsKey() {
        SimpleMap<Integer, String> map = new SimpleMap<>();
        map.insert(1, "One");
        Iterator<Integer> it = map.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(false));
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenMapIsEmptyThenThrowsException() {
        SimpleMap<Integer, String> map = new SimpleMap<>();
        Iterator<Integer> it = map.iterator();
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void shouldThrowConcurrentModificationException() {
        SimpleMap<Integer, String> map = new SimpleMap<>();
        Iterator<Integer> it = map.iterator();
        map.insert(1, "One");
        it.next();
    }

    @Test
    public void whenMapHasSeveralItemsThenIteratorShouldReturnThemInSomeOrder() {
        List<Integer> keys = Arrays.asList(1, 2, 3);
        SimpleMap<Integer, String> map = new SimpleMap<>(1);
        for (Integer key : keys) {
            map.insert(key, "test");
        }
        Iterator<Integer> it = map.iterator();
        assertThat(keys.contains(it.next()), is(true));
        assertThat(keys.contains(it.next()), is(true));
        assertThat(keys.contains(it.next()), is(true));
    }
}