package ru.job4j.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * SimpleLinkedSet Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleLinkedSetTest {
    @Test
    public void whenAddUniqueItemThenSetHasSameItem() {
        SimpleLinkedSet<String> set = new SimpleLinkedSet<>();
        set.add("One");
        set.add("Two");
        Iterator<String> it = set.iterator();
        assertThat(it.next(), is("One"));
        assertThat(it.next(), is("Two"));
    }

    @Test
    public void whenAddDuplicateThenSetHasUniqueItem() {
        SimpleLinkedSet<String> set = new SimpleLinkedSet<>();
        set.add("One");
        set.add("One");
        Iterator<String> it = set.iterator();
        assertThat(it.next(), is("One"));
        assertThat(it.hasNext(), is(false));
    }
}