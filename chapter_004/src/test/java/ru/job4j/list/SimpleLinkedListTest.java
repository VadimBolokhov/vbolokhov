package ru.job4j.list;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * SimpleLinkedList Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleLinkedListTest {
    @Test
    public void whenAddItemThenListHasSameItem() {
        SimpleLinkedList<String> list = new SimpleLinkedList<>();
        list.add("test1");
        list.add("test2");
        assertThat(list.get(1), is("test2"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsException() {
        SimpleLinkedList<String> list = new SimpleLinkedList<>();
        list.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenRemoveItemThenListHasNoSameItem() {
        SimpleLinkedList<Integer> list = new SimpleLinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.remove(1);
        assertThat(list.get(1), is(30));
        list.remove(2);
    }

    @Test
    public void whenListHasOneItemThenIteratorReturnsSameItem() {
        SimpleLinkedList<Integer> list = new SimpleLinkedList<>();
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
        SimpleLinkedList<Integer> list = new SimpleLinkedList<>();
        Iterator<Integer> it = list.iterator();
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test
    public void whenListHasSeveralItemsThenReturnsInCorrectOrder() {
        SimpleLinkedList<String> list = new SimpleLinkedList<>();
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
        SimpleLinkedList<Integer> list = new SimpleLinkedList<>();
        Iterator<Integer> it = list.iterator();
        list.add(10);
        it.next();
    }
}