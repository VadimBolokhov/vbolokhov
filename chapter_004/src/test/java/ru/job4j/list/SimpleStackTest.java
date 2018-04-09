package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * SimpleStack Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleStackTest {
    @Test
    public void whenPushItemThenPollLastAddedItem() {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        assertThat(stack.poll(), is(30));
        assertThat(stack.poll(), is(20));
        assertThat(stack.poll(), is(10));
    }
}