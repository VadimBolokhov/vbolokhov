package ru.job4j.pseudo;

import org.junit.Test;

import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Square Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SquareTest {
    /**
     * Тест метода draw()
     */
    @Test
    public void whenDrawSquareThenReturnSquare() {
        Square square = new Square();
        String expected = new StringJoiner(System.lineSeparator())
                .add("****")
                .add("*  *")
                .add("*  *")
                .add("****")
                .toString();
        assertThat(square.draw(), is(expected));
    }
}