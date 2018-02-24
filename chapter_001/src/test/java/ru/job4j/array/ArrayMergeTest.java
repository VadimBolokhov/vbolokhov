package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * ArrayMerge Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ArrayMergeTest {
    /**
     * Test merge()
     */
    @Test
    public void whenMergeTwoSortedArraysThenReturnOneBigSortedArray() {
        ArrayMerge arrayMerge = new ArrayMerge();
        int[] first = new int[] {1, 3, 5, 6, 8, 9, 10};
        int[] second = new int[] {2, 4, 7};
        int[] result = arrayMerge.merge(first, second);
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertThat(result, is(expected));
    }
}