package ru.job4j.index;

import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * WordIndex Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class WordIndexTest {
    @Test
    public void whenWordFoundThenReturnSetOfPositions() {
        WordIndex wi = new WordIndex();
        try {
            wi.loadFile("src\\test\\java\\ru\\job4j\\index\\test.txt");
            Set<Integer> result = wi.getIndexes4Word("language");
            Iterator<Integer> it = result.iterator();
            assertThat(result.size(), is(3));
            assertThat(it.next(), is(6));
            assertThat(it.next(), is(10));
            assertThat(it.next(), is(22));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @Test
    public void whenWordNotFoundThenReturnNull() {
        WordIndex wi = new WordIndex();
        try {
            wi.loadFile("src\\test\\java\\ru\\job4j\\index\\test.txt");
            Set<Integer> result = wi.getIndexes4Word("test");
            assertNull(result);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}