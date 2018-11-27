package ru.job4j.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.closeTo;

/**
 * Computer Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ComputerTest {
    private Computer computer = new Computer();

    @Test
    public void whenFunctionIsLinearThenReturnOneTwoThree() {
        List<Double> result = this.computer.diapason(1, 3, x -> x);
        List<Double> expected = Arrays.asList(1d, 2d, 3d);
        assertThat(result, is(expected));
    }

    @Test
    public void whenFunctionIsQuadraticThenOneFourNine() {
        List<Double> result = this.computer.diapason(1, 3, x -> Math.pow(x, 2));
        List<Double> expected = Arrays.asList(1d, 4d, 9d);
        assertThat(result, is(expected));
    }

    @Test
    public void whenFunctionIsLogarithmicThen() {
        List<Double> result = this.computer.diapason(1, 3, Math::log);
        assertThat(result.get(0), is(0d));
        assertThat(result.get(1), closeTo(0.7, 0.01));
        assertThat(result.get(2), closeTo(1.1, 0.01));
    }
}