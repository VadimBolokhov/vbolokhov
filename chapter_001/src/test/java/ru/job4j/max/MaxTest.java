package ru.job4j.max;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Max Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class MaxTest {
    /**
     * Test max(int first, int second) for first < second.
     */
    @Test
    public void whenFirstLessThanSecond() {
        Max maxim = new Max();
        int result = maxim.max(1, 2);
        assertThat(result, is(2));
    }
    /**
     * Test max(int first, int second) for first > second.
     */
    @Test
    public void whenFirstMoreThanSecond() {
        Max maxim = new Max();
        int result = maxim.max(2, 1);
        assertThat(result, is(2));
    }
    /**
     * Test max(int first, int second) for first == second.
     */
    @Test
    public void whenFirstEqualsToSecond() {
        Max maxim = new Max();
        int result = maxim.max(2, 2);
        assertThat(result, is(2));
    }
}
