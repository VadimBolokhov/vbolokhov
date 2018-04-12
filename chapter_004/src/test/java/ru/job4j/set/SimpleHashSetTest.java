package ru.job4j.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * SimpleHashSet Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashSetTest {
    @Test
    public void whenAddItemThenSetHasSameItem() {
        SimpleHashSet<Integer> set = new SimpleHashSet<>(1);
        set.add(1);
        set.add(2);
        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
    }

    @Test
    public void whenRemoveItemThenSetHasNoSameItem() {
        SimpleHashSet<String> set = new SimpleHashSet<>(0);
        set.add("test");
        set.remove("test");
        assertThat(set.contains("test"), is(false));
    }
}