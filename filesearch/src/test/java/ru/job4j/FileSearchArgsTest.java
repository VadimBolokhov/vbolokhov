package ru.job4j;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * FileSearch test.
 * @author Vadim Bolokhov
 */
public class FileSearchArgsTest {

    @Test(expected = ArgsException.class)
    public void whenSearchDirIsNotSpecifiedThenShouldThrowException() throws ArgsException {
        String[] args = {""};
        FileSearchArgs parser = new FileSearchArgs(args);
        parser.getSearchDir();
    }

    @Test
    public void whenSearchDirIsSpecifiedThenShouldReturnDir() throws ArgsException {
        String[] args = {"-d", "test"};
        FileSearchArgs parser = new FileSearchArgs(args);

        String result = parser.getSearchDir();

        assertThat(result, is("test"));
    }

    @Test
    public void whenTargetFileIsSpecifiedThenShouldReturnFile() throws ArgsException {
        String[] args = {"-o", "test"};
        FileSearchArgs parser = new FileSearchArgs(args);

        String result = parser.getDestination();

        assertThat(result, is("test"));
    }

    @Test(expected = ArgsException.class)
    public void whenTargetFileIsNotSpecifiedThenShouldThrowException() throws ArgsException {
        String[] args = {""};
        FileSearchArgs parser = new FileSearchArgs(args);
        parser.getDestination();
    }

    @Test
    public void whenTxtWildCardThenShouldReturnTrue() throws ArgsException {
        this.testMatcher("-m", "*.txt");
    }

    private void testMatcher(String key, String pattern) throws ArgsException {
        String[] args = {"-n", pattern, key};
        FileSearchArgs parser = new FileSearchArgs(args);
        Path path = Paths.get("test.txt");

        Predicate<Path> matcher = parser.getSearchCondition();

        boolean result = matcher.test(path);
        assertThat(result, is(true));
    }

    @Test
    public void whenRegexIsSpecifiedThenShouldReturnTrue() throws ArgsException {
        this.testMatcher("-r", ".+\\.txt\\b");
    }

    @Test
    public void whenFullMatchThenShouldReturnTrue() throws ArgsException {
        this.testMatcher("-f", "test.txt");
    }
}