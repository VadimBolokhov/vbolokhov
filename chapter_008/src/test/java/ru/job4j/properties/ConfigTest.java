package ru.job4j.properties;

import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test class for properties config.
 * @author Vadim Bolokhov
 */
public class ConfigTest {
    /** Temporary directory for test files */
    private static final Path ROOT = Paths.get(System.getProperty("java.io.tmpdir"));

    @Test
    public void whenLoadPropertiesThenShouldContainKeyAndValue() throws IOException {
        File path = this.createPropertiesFile("key=test");
        Config config = new Config(path).load();
        String result = config.value("key");
        assertThat(result, is("test"));
    }

    @Test
    public void whenPropertiesContainCommentThenShouldBeIgnored() throws IOException {
        File path = this.createPropertiesFile("#key=test");
        Config config = new Config(path).load();
        String result = config.value("key");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void whenValueIsWrappedThenResultShouldContainJoinedString() throws IOException {
        File path = this.createPropertiesFile("key1=1\\", "2\\", "3", "key2=456");
        Config config = new Config(path).load();
        assertThat(config.value("key1"), is("123"));
        assertThat(config.value("key2"), is("456"));
    }

    @Test
    public void whenChangeValueThenFileShouldContainUpdatedProperty() throws IOException {
        File path = this.createPropertiesFile("key=123");
        Config config = new Config(path).load();
        config.put("key", "456");
        config.save();
        String result;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            result = reader.readLine();
        }
        assertThat(result, is("key=456"));
    }

    @Test
    public void whenDeleteKeyThenFileNoLongerContainsTheKey() throws IOException {
        File path = this.createPropertiesFile("key=value");
        Config config = new Config(path);
        config.load();
        config.delete("key");
        config.save();
        config.load();
        assertThat(config.value("key"), is(nullValue()));
    }

    private File createPropertiesFile(String... properties) throws IOException {
        String filename = String.valueOf(System.currentTimeMillis() + ".properties");
        File path = ROOT.resolve(filename).toFile();
        if (!path.createNewFile()) {
            throw new IllegalStateException(String.format("File could not be created %s", path.getAbsoluteFile()));
        }
        try (final PrintWriter store = new PrintWriter(path)) {
            Stream.of(properties).forEach(store::println);
        }
        path.deleteOnExit();
        return path;
    }
}