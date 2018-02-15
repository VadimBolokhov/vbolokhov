package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Turn Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class TurnTest {

    @Test
    public void whenBackOneTwoThreeFourFiveThenReturnReversed() {
        Turn turn = new Turn();
        int[] array = new int[] {1, 2, 3, 4, 5};
        int[] result = turn.back(array);
        int[] expected = new int[] {5, 4, 3, 2, 1};
        assertThat(result, is(expected));
    }

    @Test
    public void whenBackFourOneSixTwoThenReturnReversed() {
        Turn turn = new Turn();
        int[] array = new int[] {4, 1, 6, 2};
        int[] result = turn.back(array);
        int[] expected = new int[] {2, 6, 1, 4};
        assertThat(result, is(expected));
    }
}