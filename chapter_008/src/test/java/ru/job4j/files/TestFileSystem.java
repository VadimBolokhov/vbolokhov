package ru.job4j.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Filesystem for file search and archiver tests.
 * @author Vadim Bolokhov
 */
public class TestFileSystem {
    private final Path root;
    private List<Path> dirs = new ArrayList<>(9);
    private List<File> testFiles = new LinkedList<>();

    public TestFileSystem(Path root) {
        this.root = root;
    }

    /**
     * To be invoked in {@code Before} method.
     * Creates a test filesystem.
     * @throws IOException if an I/O error occurs or the parent directory does not exist
     */
    public void createFileSystem() throws IOException {
        this.createDirectories();
        this.createFiles();
    }

    private void createDirectories() throws IOException {
        this.dirs.add(Files.createDirectories(this.root));
        this.createSubdirectory("1");
        this.createSubdirectory("1/1");
        this.createSubdirectory("1/2");
        this.createSubdirectory("2");
        this.createSubdirectory("2/1");
        this.createSubdirectory("2/1/1");
        this.createSubdirectory("2/1/2");
        this.createSubdirectory("2/1/3");
        this.createSubdirectory("2/2");
    }

    private void createSubdirectory(String name) {
        try {
            this.dirs.add(Files.createDirectory(this.root.resolve(name)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createFiles() throws IOException {
        this.testFiles.add(new File(dirs.get(1).resolve("1.html").toUri()));
        this.testFiles.add(new File(dirs.get(1).resolve("1.xml").toUri()));
        this.testFiles.add(new File(dirs.get(1).resolve("2.xml").toUri()));
        this.testFiles.add(new File(dirs.get(2).resolve("1.xml").toUri()));
        this.testFiles.add(new File(dirs.get(3).resolve("1.txt").toUri()));
        this.testFiles.add(new File(dirs.get(3).resolve("1.xml").toUri()));
        this.testFiles.add(new File(dirs.get(4).resolve("1.txt").toUri()));
        this.testFiles.add(new File(dirs.get(4).resolve("2.txt").toUri()));
        this.testFiles.add(new File(dirs.get(6).resolve("1.html").toUri()));
        this.testFiles.add(new File(dirs.get(6).resolve("1.txt").toUri()));
        this.testFiles.add(new File(dirs.get(6).resolve("2.txt").toUri()));
        this.testFiles.add(new File(dirs.get(8).resolve("1.xml").toUri()));
        for (File file : testFiles) {
            file.createNewFile();
        }
    }

    /**
     * To be invoked in {@code After} method.
     * Deletes all test files and directories recursively.
     */
    public void deleteFilesAndDirs() {
        this.deleteDirectory(this.root.toFile());
    }

    private void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        if (!directoryToBeDeleted.delete()) {
            System.out.println(directoryToBeDeleted);
        }
    }

    /**
     * Returns a list of all test files without directories.
     * @return files list
     */
    public List<File> getTestFiles() {
        return this.testFiles;
    }

    /**
     * Returns all test files and directories.
     * @return files and directories
     */
    public List<File> getAllFilesAndDirs() {
        return Stream.concat(
                this.dirs.stream().map(Path::toFile),
                this.testFiles.stream())
                .collect(Collectors.toList());
    }
}
