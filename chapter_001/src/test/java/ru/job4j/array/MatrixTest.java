package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Matrix Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class MatrixTest {
    /**
     * Test multiple(5)
     */
    @Test
    public void whenMultipleFiveThenMultiplicationTableFiveToFive() {
        Matrix matrix = new Matrix();
        int[][] result = matrix.multiple(5);
        int[][] expected = {
                {1, 2, 3, 4, 5},
                {2, 4, 6, 8, 10},
                {3, 6, 9, 12, 15},
                {4, 8, 12, 16, 20},
                {5, 10, 15, 20, 25}
        };
        assertThat(result, is(expected));
    }
}