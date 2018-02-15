package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * FindLoop Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class FindLoopTest {
    /**
     * Test indexOf(new int[] {1, 2, 3, 4, 5}, 4).
     */
    @Test
    public void whenIndexOfFoundFourInOneTwoThreeFourFiveThenReturnThree() {
        FindLoop fl = new FindLoop();
        int[] arr = new int[] {1, 2, 3, 4, 5};
        int result = fl.indexOf(arr, 4);
        assertThat(result, is(3));
    }

    /**
     * Test indexOf() for unexisting element.
     */
    @Test
    public void whenIndexOfNotFound() {
        FindLoop fl = new FindLoop();
        int[] arr = new int[] {1, 2, 3, 4, 5};
        int result = fl.indexOf(arr, 0);
        assertThat(result, is(-1));
    }
}
