package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Container Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ContainerTest {
    /**
     * Тест метода contains() для случая, когда слово найдено
     */
    @Test
    public void whenContainsSubStringFoundThenReturnTrue() {
        Container container = new Container();
        String origin = "Hello world";
        String sub = "world";
        boolean result = container.contains(origin, sub);
        assertThat(result, is(true));
    }

    /**
     * Тест метода contains() для случая, когда слово не найдено
     */
    @Test
    public void whenContainsSubStringNotFoundThenReturnFalse() {
        Container container = new Container();
        String origin = "Hello world";
        String sub = "word";
        boolean result = container.contains(origin, sub);
        assertThat(result, is(false));
    }
}