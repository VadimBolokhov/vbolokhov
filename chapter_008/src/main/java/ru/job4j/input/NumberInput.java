package ru.job4j.input;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Check a byte stream.
 * @author Vadim Bolokhov
 */
public class NumberInput {
    /**
     * Ensures the input stream has an even number.
     * @param in input stream
     * @return {@code true} if the input stream has an even number, otherwise - {@code false}
     * @throws IOException if an I/O exception of some sort has occurred.
     */
    public boolean isNumber(InputStream in) throws IOException {
        boolean result = false;
        try (DataInputStream input = new DataInputStream(new BufferedInputStream(in))) {
            if (input.available() >= 4) {
                int number = input.readInt();
                if (number % 2 == 0) {
                    result = true;
                }
            }
        }
        return result;
    }
}
