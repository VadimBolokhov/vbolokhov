package ru.job4j.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * SimpleSet Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleSetTest {
    @Test
    public void whenAddUniqueItemThenSetHasSameItem() {
        SimpleSet<Integer> set = new SimpleSet<>();
        set.add(10);
        set.add(20);
        Iterator<Integer> it = set.iterator();
        assertThat(it.next(), is(10));
        assertThat(it.next(), is(20));
    }

    @Test
    public void whenAddDuplicateThenSetHasUniqueItem() {
        SimpleSet<Integer> set = new SimpleSet<>();
        set.add(10);
        set.add(10);
        Iterator<Integer> it = set.iterator();
        assertThat(it.next(), is(10));
        assertThat(it.hasNext(), is(false));
    }
}