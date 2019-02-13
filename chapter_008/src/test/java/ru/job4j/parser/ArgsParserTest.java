package ru.job4j.parser;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * ArgsParser test.
 * @author Vadim Bolokhov
 */
public class ArgsParserTest {

    @Test
    public void shouldReturnFilename() {
        String fileName = "test.txt";
        String[] args = {"-d", fileName};

        Optional<String> result = new ArgsParser(args).getValue("-d");

        assertTrue(result.isPresent());
        assertThat(result.get(), is(fileName));
    }

    @Test
    public void whenGetValueOfUnregisteredKeyThenShouldReturnEmptyOptional() {
        String[] args = {};

        Optional<String> result = new ArgsParser(args).getValue("-d");

        assertFalse(result.isPresent());
    }
}