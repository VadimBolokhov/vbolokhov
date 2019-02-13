package ru.job4j.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Simple command line arguments parser.
 * @author Vadim Bolokhov
 */
public class ArgsParser {
    /** Command line args */
    private String[] args;
    /** Arguments map */
    private Map<String, String> argsMap = new HashMap<>();

    public ArgsParser(String[] args) {
        this.args = args;
        this.mapArgs();
    }

    private void mapArgs() {
        for (int i = 0; i < this.args.length; i++) {
            if (this.args[i].startsWith("-") && i + 1 < this.args.length) {
                this.argsMap.put(this.args[i], this.args[i + 1]);
            }
        }
    }

    /**
     * Returns the value to which the specified key is mapped.
     * @param key specified key
     * @return value
     */
    public Optional<String> getValue(String key) {
        return Optional.ofNullable(this.argsMap.get(key));
    }
}
