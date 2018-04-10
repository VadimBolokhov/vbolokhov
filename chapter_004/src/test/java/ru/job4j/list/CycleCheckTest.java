package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * CycleCheck Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class CycleCheckTest {
    @Test
    public void whenListHasNoCycleThenReturnFalse() {
        Node<Integer> first = new Node<>(10);
        Node<Integer> second = new Node<>(20);
        Node<Integer> third = new Node<>(30);
        Node<Integer> fourth = new Node<>(40);
        CycleCheck cycle = new CycleCheck();
        first.next = second;
        second.next = third;
        third.next = fourth;
        assertThat(cycle.hasCycle(first), is(false));
    }

    @Test
    public void whenListHasCycleThenReturnTrue() {
        Node<Integer> first = new Node<>(10);
        Node<Integer> second = new Node<>(20);
        Node<Integer> third = new Node<>(30);
        Node<Integer> fourth = new Node<>(40);
        CycleCheck cycle = new CycleCheck();
        first.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = second;
        assertThat(cycle.hasCycle(first), is(true));
    }

    @Test
    public void whenListNodeCycledOnItselfThenReturnTrue() {
        Node<Integer> node = new Node<>(10);
        node.next = node;
        CycleCheck cycle = new CycleCheck();
        assertThat(cycle.hasCycle(node), is(true));
    }
}