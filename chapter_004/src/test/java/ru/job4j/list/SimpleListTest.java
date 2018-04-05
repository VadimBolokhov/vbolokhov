package ru.job4j.list;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * SimpleList Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleListTest {
    @Test
    public void whenAddItemThenListHasSameItem() {
        SimpleList<String> list = new SimpleList<>();
        list.add("test");
        assertThat(list.get(0), is("test"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsException() {
        SimpleList<String> list = new SimpleList<>();
        list.get(0);
    }

    @Test
    public void whenListHasOneItemThenIteratorReturnsSameItem() {
        SimpleList<Integer> list = new SimpleList<>();
        list.add(10);
        Iterator<Integer> it = list.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(10));
        assertThat(it.hasNext(), is(false));
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenListIsEmptyThenThrowsException() {
        SimpleList<Integer> list = new SimpleList<>();
        Iterator<Integer> it = list.iterator();
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test
    public void whenListHasSeveralItemsThenReturnsInCorrectOrder() {
        SimpleList<String> list = new SimpleList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        Iterator<String> it = list.iterator();
        assertThat(it.next(), is("one"));
        assertThat(it.next(), is("two"));
        assertThat(it.next(), is("three"));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void shouldThrowConcurrentModificationException() {
        SimpleList<Integer> list = new SimpleList<>();
        Iterator<Integer> it = list.iterator();
        list.add(10);
        it.next();
    }
}