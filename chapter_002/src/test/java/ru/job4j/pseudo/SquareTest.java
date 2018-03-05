package ru.job4j.pseudo;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Square Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SquareTest {
    @Test
    public void whenDrawSquareThenReturnSquare() {
        Square square = new Square();
        String expected = new StringBuilder()
                .append("****\n")
                .append("*  *\n")
                .append("*  *\n")
                .append("****")
                .toString();
        assertThat(square.draw(), is(expected));
    }
}