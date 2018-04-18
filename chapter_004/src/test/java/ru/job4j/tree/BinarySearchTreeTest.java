package ru.job4j.tree;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * BinarySearchTree Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class BinarySearchTreeTest {
    @Test
    public void whenAddNewItemThenTreeHasSameItem() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(10);
        bst.add(15);
        bst.add(5);
        assertThat(bst.find(5).isPresent(), is(true));
        assertThat(bst.find(10).isPresent(), is(true));
        assertThat(bst.find(15).isPresent(), is(true));
        assertThat(bst.find(20).isPresent(), is(false));
    }

    @Test
    public void whenTreeHasOneItemThenIteratorReturnsSameItem() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(10);
        Iterator<Integer> it = bst.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(10));
        assertThat(it.hasNext(), is(false));
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenTreeIsEmptyThenShouldThrowException() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Iterator<Integer> it = bst.iterator();
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test
    public void whenTreeHasSeveralItemsThenIteratorReturnsThemInSomeOrder() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        List<Integer> list = Arrays.asList(16, 32, 4, 8, 2, 16);
        for (Integer item : list) {
            bst.add(item);
        }
        Iterator<Integer> it = bst.iterator();
        assertThat(list.contains(it.next()), is(true));
        assertThat(list.contains(it.next()), is(true));
        assertThat(list.contains(it.next()), is(true));
        assertThat(list.contains(it.next()), is(true));
        assertThat(list.contains(it.next()), is(true));
        assertThat(it.hasNext(), is(false));
    }
}