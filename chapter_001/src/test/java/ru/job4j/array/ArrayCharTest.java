package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * ArrayChar Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ArrayCharTest {
    /**
     * Тест метода startWith() для случая, когда слово начинается с префикса
     */
    @Test
    public void whenStartWithPrefixThenTrue() {
        ArrayChar word = new ArrayChar("Hello");
        boolean result = word.startWith("He");
        assertThat(result, is(true));
    }

    /**
     * Тест метода startWith() для случая, когда слово НЕ начинается с префикса
     */
    @Test
    public void whenNotStartWithPrefixThenFalse() {
        ArrayChar word = new ArrayChar("Hello");
        boolean result = word.startWith("Hi");
        assertThat(result, is(false));
    }
}