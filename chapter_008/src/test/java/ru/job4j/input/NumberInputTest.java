package ru.job4j.input;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * NumberInput test.
 * @author Vadim Bolokhov
 */
public class NumberInputTest {

    @Test
    public void whenThreeThenShouldReturnFalse() throws IOException {
        final int three = 3;
        ByteBuffer number = ByteBuffer.allocate(4).putInt(three);
        try (ByteArrayInputStream input = new ByteArrayInputStream(number.array())) {
            NumberInput ni = new NumberInput();

            boolean result = ni.isNumber(input);

            assertFalse(result);
        }
    }

    @Test
    public void whenFourThenShouldReturnTrue() throws IOException {
        final int four = 4;
        ByteBuffer number = ByteBuffer.allocate(4).putInt(four);
        try (ByteArrayInputStream input = new ByteArrayInputStream(number.array())) {
            NumberInput ni = new NumberInput();

            boolean result = ni.isNumber(input);

            assertTrue(result);
        }
    }

    @Test
    public void whenLessThenFourBytesThenShouldReturnFalse() throws IOException {
        try (ByteArrayInputStream input = new ByteArrayInputStream("0".getBytes())) {
            NumberInput ni = new NumberInput();

            boolean result = ni.isNumber(input);

            assertFalse(result);
        }
    }
}