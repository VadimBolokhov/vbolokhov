package ru.job4j.pseudo;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Triangle Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class TriangleTest {
    @Test
    public void whenDrawTriangleThenReturnTriangle() {
        Triangle triangle = new Triangle();
        String expected = new StringBuilder()
                .append("  ^  \n")
                .append(" / \\ \n")
                .append("/___\\")
                .toString();
        assertThat(triangle.draw(), is(expected));
    }
}