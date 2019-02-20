package ru.job4j.chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Simple file based chat bot.
 * @author Vadim Bolokhov
 */
public class FileBasedAI implements ChatAI {
    /** Random number generator */
    private static final Random RANDOM = new Random();
    /** The list of strings */
    List<String> phrases = new ArrayList<>();

    public FileBasedAI(String filename) throws IOException {
        this(new File(filename));
    }

    public FileBasedAI(File input) throws IOException {
        this.getPhraseList(input);
    }

    private void getPhraseList(File input) throws IOException {
        try (FileInputStream in = new FileInputStream(input);
             Scanner scanner = new Scanner(in)) {
            while (scanner.hasNextLine()) {
                this.phrases.add(scanner.next());
            }
        }
    }

    @Override
    public String discuss(String message) {
        int index = RANDOM.nextInt(this.phrases.size());
        return this.phrases.get(index);
    }
}
