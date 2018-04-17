package ru.job4j.tree;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Tree Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class TreeTest {
    @Test
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true));
    }

    @Test
    public void when6ElFindNotExistThenOptionEmpty() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenTreeIsBinaryThenReturnTrue() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(3, 4);
        assertThat(tree.isBinary(), is(true));

    }

    @Test
    public void whenTreeIsNotBinaryThenReturnFalse() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(2, 3);
        tree.add(2, 4);
        tree.add(2, 5);
        assertThat(tree.isBinary(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenTreeHasOneItemThenIteratorReturnsSameItem() {
        Tree<Integer> tree = new Tree<>(1);
        Iterator<Integer> it = tree.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), Matchers.is(false));
        assertThat(it.hasNext(), Matchers.is(false));
        it.next();
    }

    @Test
    public void whenTreeHasSeveralItemsThenReturnsInSomeOrder() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        Iterator<Integer> it = tree.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
    }
}