package ru.job4j.files;

import ru.job4j.parser.ArgsParser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Command line arguments handler.
 * @author Vadim Bolokhov
 */
public class Args {
    /** Command line arguments parser */
    private ArgsParser parser;

    public Args(String[] args) {
        this.parser = new ArgsParser(args);
    }

    /**
     * Returns source directory.
     * @return source directory
     */
    public String directory() {
        return this.getPath("-d");
    }

    private String getPath(String key) {
        Optional<String> value = this.parser.getValue(key);
        if (!value.isPresent()) {
            throw new IllegalArgumentException("Invalid command line parameters.");
        }
        return value.get();
    }

    /**
     * Returns destination file.
     * @return destination file
     */
    public String output() {
        return this.getPath("-o");
    }

    /**
     * Returns list of extensions to be excluded.
     * @return list of extensions
     */
    public List<String> exclude() {
        Optional<String> extArg = this.parser.getValue("-e");
        return extArg.map(s -> Arrays.asList(s.split(",")))
                .orElseGet(LinkedList::new);
    }

}
