package ru.job4j.jdbc;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * MagnetApp Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@Ignore
public class MagnetAppTest {

    @Test
    public void whenRunApplicationWithArgumentFourThenReturnTen() throws Exception {
        PrintStream stdout = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        MagnetApp.main(new String[] {String.valueOf(4)});
        int result = Integer.parseInt(new String(out.toByteArray()).trim());
        assertThat(result, is(10));
        System.setOut(stdout);
    }
}