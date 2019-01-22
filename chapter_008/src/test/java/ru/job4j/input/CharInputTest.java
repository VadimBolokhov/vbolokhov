package ru.job4j.input;

import org.junit.Test;

import java.io.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * CharInput test.
 * @author Vadim Bolokhov
 */
public class CharInputTest {
    @Test
    public void shouldRemoveWordOne() throws IOException {
        String[] abuse = {"one"};
        String phrase = "One two three";
        try (InputStream in = new ByteArrayInputStream(phrase.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CharInput ci = new CharInput();

            ci.dropAbuses(in, out, abuse);
            String result = out.toString();

            assertThat(result, is(" two three"));
        }
    }

    @Test
    public void shouldRemoveWordsTwoAndFourCaseInsensitive() throws IOException {
        String[] abuse = {"two", "FOUR"};
        String phrase = "One two three four";
        try (InputStream in = new ByteArrayInputStream(phrase.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CharInput ci = new CharInput();

            ci.dropAbuses(in, out, abuse);
            String result = out.toString();

            assertThat(result, is("One  three "));
        }
    }

    @Test
    public void shouldRemoveWholeWordsOnly() throws IOException {
        String[] abuse = {"two", "three"};
        String phrase = "Onetwo three four";
        try (InputStream in = new ByteArrayInputStream(phrase.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CharInput ci = new CharInput();

            ci.dropAbuses(in, out, abuse);
            String result = out.toString();

            assertThat(result, is("Onetwo  four"));
        }
    }
}