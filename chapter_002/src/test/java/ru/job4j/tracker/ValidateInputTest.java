package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

/**
 * ValidateInput Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ValidateInputTest {
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    @Before
    public void loadMem() {
        System.setOut(new PrintStream(this.mem));
    }

    @After
    public void loadSys() {
        System.setOut(this.out);
    }

    /**
     * Тест метода ask(), в случае некорректного ввода
     */
    @Test
    public void whenInvalidInput() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[] {"invalid", "1"})
        );
        input.ask("Enter", new int[] {1});
        assertThat(
                this.mem.toString(),
                is(
                        String.format("Введите корректное значение%n")
                )
        );
    }

    /**
     * Тест метода ask(), когда выбран несуществующий пункт меню
     */
    @Test
    public void whenOutOfMenuRangeInput() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[] {"2", "1"})
        );
        input.ask("Enter", new int[] {1});
        assertThat(
                this.mem.toString(),
                is(
                        String.format("Пункта меню не существует%n")
                )
        );
    }
}