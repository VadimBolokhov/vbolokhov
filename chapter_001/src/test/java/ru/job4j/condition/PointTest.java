package ru.job4j.condition;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class PointTest {
    /**
     * Test distanceTo()
     */
    @Test
    public void whenFirstZeroZeroSecondThreeFourThenFive() {
        Point a = new Point(0,0);
        Point b = new Point(3,4);
        double result = a.dinstanceTo(b);
        double expected = 5D;
        assertThat(result, is(expected));
    }
}
