package ru.job4j.files;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;

/**
 * File system search.
 * @author Vadim Bolokhov
 */
public class Search {

    /**
     * Searches for files with specified extensions in the specified directory
     * and subdirectories.
     * If extensions list is empty, returns all files and subdirectories.
     * @param parent initial search directory
     * @param exts list of extensions
     * @return search result
     */
    public List<File> files(String parent, List<String> exts) {
        List<File> result;
        if (this.isExtensionsListSpecified(exts)) {
            String regex = String.format(".+\\.(%s)\\b", String.join("|", exts));
            result = this.getFiles(parent, regex, (f, l) -> { });
        } else {
            String regex = ".+";
            result = this.getFiles(parent, regex, (f, l) -> l.add(f));
        }
        return result;
    }

    private boolean isExtensionsListSpecified(List<String> exts) {
        return exts != null && !exts.isEmpty();
    }

    private List<File> getFiles(String parent, String regex, BiConsumer<File, List<File>> func) {
        List<File> result = new LinkedList<>();
        if (this.isParentExists(parent)) {
            for (Queue<File> fileQueue = this.createFileQueue(parent); !fileQueue.isEmpty();) {
                File current = fileQueue.poll();
                if (current.isDirectory()) {
                    func.accept(current, result);
                    File[] content = current.listFiles();
                    if (content != null) {
                        Arrays.stream(content).forEach(fileQueue::offer);
                    }
                } else {
                    if (current.getName().matches(regex)) {
                        result.add(current);
                    }
                }
            }
            this.removeParent(result);
        }
        return result;
    }

    private boolean isParentExists(String parent) {
        return Files.exists(Paths.get(parent));
    }

    private Queue<File> createFileQueue(String parent) {
        Queue<File> files = new LinkedList<>();
        files.offer(new File(parent));
        return files;
    }

    private void removeParent(List<File> result) {
        if (!result.isEmpty() && result.get(0).isDirectory()) {
            result.remove(0);
        }
    }
}
