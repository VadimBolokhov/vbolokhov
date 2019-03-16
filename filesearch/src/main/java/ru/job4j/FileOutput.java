package ru.job4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * File Output.
 * @author Vadim Bolokhov
 */
public class FileOutput {
    /** A file to write */
    private final Path output;

    public FileOutput(Path output) {
        this.output = output;
    }

    /**
     * Writes list of strings to the output file.
     * @param strings Strings list
     * @throws IOException if an I/O error occurs
     */
    public void writeStrings(List<String> strings) throws IOException {
        Path parent = this.output.getParent();
        if (parent != null) {
            Files.createDirectories(this.output.getParent());
        }
        try (FileWriter fileWriter = new FileWriter(this.output.toFile());
        PrintWriter writer = new PrintWriter(fileWriter)) {
            strings.forEach(writer::println);
        }
    }
}
