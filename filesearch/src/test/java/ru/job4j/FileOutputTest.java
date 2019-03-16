package ru.job4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * FileOutput test.
 * @author Vadim Bolokhov
 */
public class FileOutputTest {
    private static final Path ROOT = Paths.get(System.getProperty("java.io.tmpdir"))
            .resolve("job4j");

    private Path file = ROOT.resolve("test.txt");

    @Before
    public void createTempDir() throws IOException {
        Files.createDirectories(ROOT);
    }

    @After
    public void deleteTempDir() throws IOException {
        Files.deleteIfExists(this.file);
        Files.delete(ROOT);
    }

    @Test
    public void whenWriteToFileThenFileShouldContainString() throws IOException {
        List<String> strings = Collections.singletonList("test");
        FileOutput out = new FileOutput(this.file);

        out.writeStrings(strings);

        String result = Files.readAllLines(this.file).get(0);
        assertThat(result, is("test"));
    }
}