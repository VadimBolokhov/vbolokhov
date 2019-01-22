package ru.job4j.input;

import java.io.*;

/**
 * Check a char stream.
 * @author Vadim Bolokhov
 */
public class CharInput {

    /**
     * Removes abuses from the input stream.
     * @param in input stream
     * @param out output stream (without abuses)
     * @param abuse array of abuses to be removed from the input stream
     */
    public void dropAbuses(InputStream in, OutputStream out, String[] abuse) throws IOException {
        String regex = String.format("\\b(?i)%s\\b", String.join("|", abuse));
        try (BufferedReader input = new BufferedReader(new InputStreamReader(in));
                PrintWriter output = new PrintWriter(out)) {
            input.lines()
                    .map(line -> line.replaceAll(regex, ""))
                    .forEach(output::write);
        }
    }
}
