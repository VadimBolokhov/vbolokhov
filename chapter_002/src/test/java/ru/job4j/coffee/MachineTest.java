package ru.job4j.coffee;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Coffee machine Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class MachineTest {

    /**
     * Тест метода changes()
     */
    @Test
    public void when() {
        Machine coffee = new Machine();
        try {
            int[] result = coffee.changes(50, 22);
            int[] expected = {10, 10, 5, 2, 1};
            assertThat(result, is(expected));
        } catch (IncorrectValueException ive) {
            System.out.println(ive);
        }
    }
}