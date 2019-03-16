package ru.job4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * File Search class.
 * @author Vadim Bolokhov
 */
public class FileSearch {
    /** A predicate that defines if a file satisfies
     * the search condition */
    private Predicate<Path> matcher;

    public FileSearch(Predicate<Path> matcher) {
        this.matcher = matcher;
    }

    /**
     * Perform file search.
     * @param root search directory
     * @return a list of files that satisfy a search condition
     * @throws IOException if an I/O error occurs
     */
    public List<Path> search(Path root) throws IOException {
        List<Path> result = new LinkedList<>();
        Queue<Path> pathQueue = new LinkedList<>();
        pathQueue.offer(root);
        while (!pathQueue.isEmpty()) {
            Path current = pathQueue.poll();
            if (Files.isDirectory(current)) {
                try (Stream<Path> content = Files.list(current)) {
                    content.forEach(pathQueue::offer);
                }
            }
            if (this.matcher.test(current)) {
                result.add(current);
            }
        }
        return result;
    }
}
