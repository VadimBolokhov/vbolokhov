package ru.job4j.search;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * ConvertList Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ConvertListTest {
    /**
     * Тест метода toArray()
     */
    @Test
    public void whenConvertToArray() {
        ConvertList convert = new ConvertList();
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            list.add(i);
        }
        int[][] result = convert.toArray(list, 3);
        int[][] expected = {{1, 2, 3}, {4, 5, 6}, {7, 0, 0}};
        assertThat(result, is(expected));
    }

    /**
     * Тест метода toList()
     */
    @Test
    public void whenConvertToList() {
        ConvertList convert = new ConvertList();
        int[][] array = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        List<Integer> result = convert.toList(array);
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            expected.add(i);
        }
        assertThat(result, is(expected));
    }

    /**
     * Тест метода convert()
     */
    @Test
    public void whenConvertListOfArraysToIntegerList() {
        List<int[]> list = new ArrayList<>();
        ConvertList convert = new ConvertList();
        list.add(new int[] {1, 2});
        list.add(new int[] {3, 4, 5, 6});
        List<Integer> result = convert.convert(list);
        List<Integer> expected = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            expected.add(i);
        }
        assertThat(result, is(expected));
    }
}