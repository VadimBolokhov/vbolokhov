package ru.job4j.chat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Chat output to file.
 * @author Vadim Bolokhov
 */
public class FileChatOut implements ChatOut, AutoCloseable {
    /** Output */
    private BufferedWriter writer;

    FileChatOut(String fileName) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(fileName));
    }

    @Override
    public void display(String message) throws IOException {
        this.writer.append(message)
                .append(System.lineSeparator());
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
