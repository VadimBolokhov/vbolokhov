package ru.job4j.list;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * SimpleQueue Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleQueueTest {
    @Test
    public void whenPushItemToQueueThenPollFirstAddedItem() {
        SimpleQueue<String> queue = new SimpleQueue<>();
        queue.push("One");
        queue.push("Two");
        queue.push("Three");
        assertThat(queue.poll(), is("One"));
        assertThat(queue.poll(), is("Two"));
        assertThat(queue.poll(), is("Three"));
    }
}