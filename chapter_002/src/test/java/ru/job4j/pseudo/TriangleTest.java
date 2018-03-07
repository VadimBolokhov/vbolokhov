package ru.job4j.pseudo;

import org.junit.Test;

import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Triangle Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class TriangleTest {
    /**
     * Тест метода draw()
     */
    @Test
    public void whenDrawTriangleThenReturnTriangle() {
        Triangle triangle = new Triangle();
        String expected = new StringJoiner(System.lineSeparator())
                .add("  ^  ")
                .add(" / \\ ")
                .add("/___\\")
                .toString();
        assertThat(triangle.draw(), is(expected));
    }
}