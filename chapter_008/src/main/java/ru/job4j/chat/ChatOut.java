package ru.job4j.chat;

import java.io.IOException;

/**
 * Chat output interface.
 * @author Vadim Bolokhov
 */
public interface ChatOut {
    /**
     * Sends the message to device(s).
     * @param message Message to display.
     * @throws IOException If an I/O error occurs.
     */
    void display(String message) throws IOException;
}
