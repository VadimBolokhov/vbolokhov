package ru.job4j.files;

import java.io.IOException;
import java.util.List;

/**
 * Archiver interface.
 * @author Vadim Bolokhov
 */
public interface Archiver {
    /**
     * Writes files from a source directory to specified file.
     * @param source directory to be archived
     * @param dest destination
     * @param exts list of extensions to be excluded
     * @throws IOException if an I/O error occurs or the parent directory does not exist
     */
    void pack(String source, String dest, List<String> exts) throws IOException;
}
