package ru.job4j.files;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * File System search test.
 * @author Vadim Bolokhov
 */
public class SearchTest {
    /** Test filesystem directory */
    private static final Path ROOT = Paths.get(System.getProperty("java.io.tmpdir")).resolve("job4j");
    /** List of test files */
    private List<File> testFiles;
    /** Test filesystem */
    private TestFileSystem fileSystem = new TestFileSystem(ROOT);

    @Before
    public void createFileSystem() throws IOException {
        this.fileSystem.createFileSystem();
        this.testFiles = this.fileSystem.getTestFiles();
    }

    @After
    public void deleteFilesAndDirs() {
        this.fileSystem.deleteFilesAndDirs();
    }

    @Test
    public void whenSearchForTxtThenShouldReturnListOfTxt() {
        List<String> extensions = Collections.singletonList("txt");
        Search fileSearch = new Search();
        File[] expectedFiles = this.testFiles.stream()
                .filter(file -> {
                    String name = file.getName();
                    String extension = name.substring(name.lastIndexOf('.') + 1);
                    return extension.equals(extensions.get(0));
                }).toArray(File[]::new);

        List<File> result = fileSearch.files(ROOT.toString(), extensions);

        assertThat(result, containsInAnyOrder(expectedFiles));
    }

    @Test
    public void whenSearchForXmlAndHtmlThenShouldReturnListOfXmlAndHtml() {
        List<String> extensions = Arrays.asList("xml", "html");
        Search fileSearch = new Search();
        File[] expectedFiles = this.testFiles.stream()
                        .filter(file -> {
                            String name = file.getName();
                            String extension = name.substring(name.lastIndexOf('.') + 1);
                            return extensions.contains(extension);
                        }).toArray(File[]::new);

        List<File> result = fileSearch.files(ROOT.toString(), extensions);

        assertThat(result, containsInAnyOrder(expectedFiles));
    }

    @Test
    public void whenTargetIsMissingThenShouldReturnEmptyList() {
        List<String> extensions = Collections.singletonList("jpg");
        Search fileSearch = new Search();

        List<File> result = fileSearch.files(ROOT.toString(), extensions);

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenExtensionsAreNotSpecifiedThenReturnAllDirsAndFiles() {
        Search fileSearch = new Search();
        List<File> expected = this.fileSystem.getAllFilesAndDirs();
        expected.remove(0);

        List<File> result = fileSearch.files(ROOT.toString(), new LinkedList<>());

        assertTrue(result.containsAll(expected));
    }
}