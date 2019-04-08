package ru.job4j.properties;

import java.io.*;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The class represents a persistent set of properties.
 * @author Vadim Bolokhov
 */
public class Config {
    /** The character that separates the key from the value  */
    private static final String SP = "=";
    /** The character a comment line starts from */
    private static final String COMMENT = "#";
    /** The properties file */
    private final File path;
    /** The properties map */
    private final Map<String, String> properties = new LinkedHashMap<>();

    private StringBuilder buffer = new StringBuilder();

    public Config(String path) {
        this(new File(path));
    }

    public Config(File path) {
        this.path = path;
    }

    public Config(Path path) {
        this.path = path.toFile();
    }

    /**
     * Reads a property list (key and element pairs) from the file.
     * @return This config
     */
    public Config load() {
        this.properties.clear();
        try (BufferedReader file = new BufferedReader(new FileReader(this.path))) {
            file.lines()
                    .filter(this::isNotComment)
                    .forEach(this::parseProperty);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private boolean isNotComment(String line) {
        return !line.trim().startsWith(COMMENT);
    }

    private void parseProperty(String line) {
        line = line.trim();
        if (line.endsWith("\\")) {
            line = line.substring(0, line.length() - 1);
            this.buffer.append(line);
        } else if (this.buffer.length() > 0) {
            this.buffer.append(line);
            this.putValue(this.buffer.toString());
            this.buffer.setLength(0);
        } else {
            this.putValue(line);
        }

    }

    private void putValue(String line) {
        if (line.matches(".*" + SP + ".+")) {
            int pos = line.indexOf(SP);
            String key = line.substring(0, pos).trim();
            String value = line.substring(pos + 1).trim();
            this.properties.put(key, value);
        }
    }

    /**
     * Writes this property list (key and element pairs) back to the file
     */
    public void save() {
        try (final PrintWriter file = new PrintWriter(this.path)) {
            this.properties.forEach(
                    (key, value) -> {
                        file.append(key)
                                .append(SP)
                                .append(value);
                        file.println();
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Associates the specified value with the specified key.
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void put(String key, String value) {
        this.properties.put(key, value);
    }

    /**
     * Searches for the property with the specified key in this property list.
     * @param key the property key.
     * @return the value in this property list with the specified key value.
     */
    public String value(String key) {
        return this.properties.get(key);
    }

    /**
     * Removes the mapping for a key from the map if it is present.
     * @param key key whose mapping is to be removed from the map
     */
    public void delete(String key) {
        this.properties.remove(key);
    }
}
