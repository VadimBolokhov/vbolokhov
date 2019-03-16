package ru.job4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * FileOutput test.
 * @author Vadim Bolokhov
 */
public class FileSearchTest {
    /** Test filesystem directory */
    private static final Path ROOT = Paths.get(System.getProperty("java.io.tmpdir")).resolve("job4j");

    private Path testFile = ROOT.resolve("test.txt");
    private Path output = ROOT.resolve("out.txt");
    private final PrintStream stdOut = System.out;

    @Before
    public void createFile() throws IOException {
        Files.createDirectories(ROOT);
        Files.createFile(this.testFile);
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    @After
    public void cleanUpTestDirectory() throws IOException {
        Files.deleteIfExists(this.testFile);
        Files.deleteIfExists(this.output);
        Files.deleteIfExists(ROOT);
        System.setOut(stdOut);
    }

    @Test
    public void whenNoFilesFoundThenNoDestinationFileShouldBeCreated() {
        String[] args = {"-d", ROOT.toString(), "-n", "1.jpg",
                "-f", "-o", this.output.toString()};

        SearchStart.main(args);

        assertFalse(Files.exists(this.output));
    }

    @Test
    public void whenSearchByTxtWildcardThenShouldReturnTrue() throws IOException {
        String[] args = {"-d", ROOT.toString(), "-n", "*.txt",
                "-m", "-o", this.output.toString()};

        SearchStart.main(args);

        String result = Files.readAllLines(this.output).get(0);
        assertThat(result, is(this.testFile.toString()));
    }

    @Test
    public void whenBooleanKeyNotFoundThenNoDestinationShouldBeCreated() throws IOException {
        String[] args = {"-d", ROOT.toString(), "-n", ".+\\.txt\\b",
                "-o", this.output.toString()};

        SearchStart.main(args);

        assertFalse(Files.exists(this.output));
    }
}