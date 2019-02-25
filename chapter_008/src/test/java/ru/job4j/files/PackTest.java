package ru.job4j.files;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Archiver test.
 * @author Vadim Bolokhov
 */
public class PackTest {
    /** Test filesystem directory */
    private static final Path ROOT = Paths.get(System.getProperty("java.io.tmpdir")).resolve("job4j");
    /** Test filesystem */
    private final TestFileSystem fs = new TestFileSystem(ROOT);

    @Before
    public void createFileSystem() throws IOException {
        this.fs.createFileSystem();
    }

    @After
    public void deleteFilesAndDirectories() {
        this.fs.deleteFilesAndDirs();
    }

    @Test
    public void whenSpecifyTxtFilesThenResultFileShouldNotContainTxt() throws IOException {
        Path zipFile = ROOT.resolve("out/project.zip");
        String[] args = {"-d", ROOT.toString(), "-e", "txt", "-o", zipFile.toString()};
        long expectedSize = this.fs.getAllFilesAndDirs().stream()
                .filter(file -> !file.getName().contains(".txt"))
                .count();

        Pack.main(args);

        List<ZipEntry> result = this.getZipEntries(zipFile);
        long resultSize = (long) result.size();
        assertThat(resultSize, is(expectedSize));
    }

    private List<ZipEntry> getZipEntries(Path zipFile) throws IOException {
        List<ZipEntry> result = new LinkedList<>();
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile.toString()))) {
            for (ZipEntry entry = zin.getNextEntry(); entry != null; zin.closeEntry(), entry = zin.getNextEntry()) {
                result.add(entry);
            }
        }
        return result;
    }

    @Test
    public void whenExtensionsAreNotSpecifiedThenShouldPackWholeDirectory() throws IOException {
        Path zipFile = ROOT.resolve("out/project.zip");
        String[] args = {"-d", ROOT.toString(), "-o", zipFile.toString()};
        int expectedSize = this.fs.getAllFilesAndDirs().size();

        Pack.main(args);

        int resultSize = this.getNumberOfZipEntries(zipFile);
        assertThat(resultSize, is(expectedSize));
    }

    private int getNumberOfZipEntries(Path zipFile) throws IOException {
        try (ZipFile result = new ZipFile(zipFile.toFile())) {
            return result.size();
        }
    }

    @Test
    public void whenSourceDirectoryDoesNotExistsThenNoOutputFileShouldBeCreated() throws IOException {
        String source = ROOT.resolve("test").toString();
        Path zipFile = ROOT.resolve("out/project.zip");
        String[] args = {"-d", source, "-o", zipFile.toString()};

        Pack.main(args);

        assertFalse(Files.exists(zipFile));
    }
}