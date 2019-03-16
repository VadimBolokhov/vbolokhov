package ru.job4j;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

/**
 * Command line arguments parser.
 * @author Vadim Bolokhov
 */
public class FileSearchArgs {
    /** Command line arguments map */
    Map<String, Integer> argsMap = new HashMap<>();
    /** Command line arguments */
    List<String> args;
    /** List of boolean command line keys */
    List<String> booleanKeys = Arrays.asList("-f", "-m", "-r");

    public FileSearchArgs(String[] args) {
        this.parseArgs(args);
        this.args = Arrays.asList(args);
    }

    private void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                this.argsMap.put(args[i], i);
            }
        }
    }

    /**
     * Returns a value that is mapped to '-d' key
     * @return Search directory
     * @throws ArgsException if the key or search directory is not specified
     */
    public String getSearchDir() throws ArgsException {
        return this.getValueIfPresent("-d", ArgsException.ErrorCode.SEARCH_DIR_MISSING);
    }

    private String getValueIfPresent(String key, ArgsException.ErrorCode errorCode) throws ArgsException {
        Integer index = this.argsMap.get(key);
        if (index == null || this.isTheLastIndex(index)) {
            throw new ArgsException(errorCode);
        }
        return this.args.get(index + 1);
    }

    private boolean isTheLastIndex(int index) {
        return index == this.args.size() - 1;
    }

    /**
     * Returns a destination file
     * @return Destination file
     * @throws ArgsException if the key or destination file is not specified
     */
    public String getDestination() throws ArgsException {
        return this.getValueIfPresent("-o", ArgsException.ErrorCode.DESTINATION_MISSING);
    }

    /**
     * Returns a search condition
     * @return File search condition
     * @throws ArgsException if '-n' key is present but no search condition
     * is specified
     */
    public Predicate<Path> getSearchCondition() throws ArgsException {
        Predicate<Path> result = path -> true;
        Integer index = this.argsMap.get("-n");
        if (index != null) {
            boolean isMatcherKeyFound = false;
            String pattern = this.getPatternValue(index);
            for (String key : this.booleanKeys) {
                if (this.argsMap.containsKey(key)) {
                    result = new MatcherFactory(pattern).getMatcher(key);
                    isMatcherKeyFound = true;
                    break;
                }
            }
            if (!isMatcherKeyFound) {
                throw new ArgsException(ArgsException.ErrorCode.PATTERN_MISSING);
            }
        }
        return result;
    }

    private String getPatternValue(int index) throws ArgsException {
        if (this.isTheLastIndex(index)) {
            throw new ArgsException(ArgsException.ErrorCode.PATTERN_MISSING);
        }
        return this.args.get(index + 1);
    }
}
