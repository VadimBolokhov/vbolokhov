package ru.job4j.search;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * ParallelSearch Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ParallelSearchTest {
    @Test
    public void whenTextFoundThenReturnList() {
        List<String> exts = new ArrayList<>();
        exts.add("txt");
        ParallelSearch ps = new ParallelSearch(
                "src\\test\\java\\ru\\job4j\\search",
                "evolution",
                exts
        );
        ps.init();
        String expected = "src\\test\\java\\ru\\job4j\\search\\test.txt";
        assertThat(ps.result().get(0), is(expected));
    }
}