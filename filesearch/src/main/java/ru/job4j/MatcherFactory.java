package ru.job4j;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Matchers factory class.
 * @author Vadim Bolokhov
 */
public class MatcherFactory {
    /** The file search pattern */
    private String pattern;
    /** Boolean keys map */
    private Map<String, Predicate<Path>> matchers = new HashMap<>();

    public MatcherFactory(String pattern) {
        this.pattern = pattern;
        this.addMatchers();
    }

    private void addMatchers() {
        this.matchers.put("-f", this::getFullMatcher);
        this.matchers.put("-r", this::getRegexMatcher);
        this.matchers.put("-m", this::getWildCardMatcher);
    }

    /**
     * Returns a file search condition that is mapped to the specified key
     * @param key command line key
     * @return search condition
     */
    public Predicate<Path> getMatcher(String key) {
        return this.matchers.get(key);
    }

    private boolean getFullMatcher(Path path) {
        return path.toString().endsWith(pattern);
    }

    private boolean getRegexMatcher(Path path) {
        PathMatcher matcher = FileSystems.getDefault()
                .getPathMatcher("regex:" + pattern);
        return matcher.matches(path.getFileName());
    }

    private boolean getWildCardMatcher(Path path) {
        PathMatcher matcher = FileSystems.getDefault()
                .getPathMatcher("glob:" + pattern);
        return matcher.matches(path.getFileName());
    }
}
