package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Square Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SquareTest {
    /**
     * Test calculate(5).
     */
    @Test
    public void whenCalculateBoundFiveThenOneFourNineSixteenTwentyFive() {
        Square square = new Square();
        int[] result = square.calculate(5);
        int[] expected = new int[] {1, 4, 9, 16, 25};
        assertThat(result, is(expected));
    }
}
