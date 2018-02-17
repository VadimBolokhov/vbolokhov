package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * ArrayDuplicate Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicateTest {
    /**
     * Проверка метода remove() для случая, когда в массиве есть повторяющиеся элементы
     */
    @Test
    public void whenRemoveDuplicatesThenReturnArrayWithoutDuplicates() {
        ArrayDuplicate ad = new ArrayDuplicate();
        String[] array = {"One", "Two", "Two", "Three", "Two", "Four", "Five", "Six", "Six"};
        String[] result = ad.remove(array);
        String[] expected = {"One", "Two", "Three", "Four", "Five", "Six"};
        assertThat(result, is(expected));
    }

    /**
     * Проверка метода remove() для случая, когда в массиве нет повторяющихся элементов
     */
    @Test
    public void whenRemoveNoDuplicatesThenReturnArray() {
        ArrayDuplicate ad = new ArrayDuplicate();
        String[] array = {"One", "Two", "Three", "Four", "Five"};
        String[] result = ad.remove(array);
        String[] expected = {"One", "Two", "Three", "Four", "Five"};
        assertThat(result, is(expected));
    }
}