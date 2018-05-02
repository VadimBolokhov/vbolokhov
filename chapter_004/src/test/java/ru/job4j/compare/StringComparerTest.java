package ru.job4j.compare;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * StringComparer Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class StringComparerTest {
    @Test
    public void whenStringsHasSameCharSetsThenReturnTrue() {
        StringComparer sc = new StringComparer();
        assertThat(sc.compareStrings("forty five", "over fifty"), is(true));
    }

    @Test
    public void whenStringsHasDifferentCharSetsThenReturnFalse() {
        StringComparer sc = new StringComparer();
        assertThat(sc.compareStrings("mama", "papa"), is(false));
        assertThat(sc.compareStrings("ten", "twenty"), is(false));
    }
}
