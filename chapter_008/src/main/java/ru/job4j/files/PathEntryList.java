package ru.job4j.files;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * An iterable set of files and directories
 * @author Vadim Bolokhov
 */
public class PathEntryList implements Iterable<PathEntryList.PathEntry> {
    /** Source directory tree */
    private final TreeSet<PathEntry> entrySet = new TreeSet<>();
    /** Index of the last name element of source path */
    private final int rootIndex;

    public PathEntryList(Path source) {
        this.rootIndex = source.getNameCount() - 1;
    }

    /**
     * Adds path to the PathEntryList.
     * @param path path to be added
     */
    public void addPath(Path path) {
        this.entrySet.add(new PathEntry(path));
    }

    /**
     * Removes specified path from the PathEntryList.
     * @param path path to be removed
     */
    public void removePath(Path path) {
        this.entrySet.remove(path);
    }

    /**
     * Checks whether the PathEntryList contains any files or not
     * @return {@code true} if PathEntryList is not empty
     */
    public boolean containsFiles() {
        return !this.entrySet.isEmpty();
    }

    @Override
    public Iterator<PathEntry> iterator() {
        return this.entrySet.iterator();
    }

    /**
     * Class that contains path information
     * @author Vadim Bolokhov
     */
    public class PathEntry implements Comparable<PathEntry> {
        private final Path path;

        public PathEntry(Path path) {
            this.path = path;
        }

        public Path getPath() {
            return this.path;
        }

        /**
         * Returns a string that can be used as ZipEntry parameter
         * @return entry name
         */
        public String getEntryName() {
            return this.path.subpath(
                    PathEntryList.this.rootIndex,
                    this.path.getNameCount()
            ).toString();
        }

        @Override
        public int compareTo(PathEntry o) {
            return this.path.compareTo(o.path);
        }
    }
}
