package ru.job4j.chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Console chat.
 * @author Vadim Bolokhov
 */
public class Chat {
    /** Traditional chat greeting */
    public static final String CHAT_GREETING = "Введите сообщение:";
    /** Exit chat */
    public static final String EXIT_WORD = "закончить";
    /** Suspend bot */
    public static final String SUSPEND_WORD = "стоп";
    /** Resume bot */
    public static final String RESUME_WORD = "продолжить";

    /** Chat output */
    private ChatOut out;
    /** Chat bot */
    private ChatAI bot;
    /** Chat reserved words */
    private Map<String, ChatMode> keywords = new HashMap<>();
    /** Chat mode */
    private ChatMode chatMode;

    public Chat(ChatOut out, ChatAI bot) {
        this.out = out;
        this.bot = bot;
        this.addKeywords();
    }

    private void addKeywords() {
        this.keywords.put(EXIT_WORD, ChatMode.EXIT);
        this.keywords.put(SUSPEND_WORD, ChatMode.SILENT);
        this.keywords.put(RESUME_WORD, ChatMode.SPEAKER);
    }

    /**
     * Main cycle. Provides interaction between user and chat bot.
     * @throws IOException If an I/O error occurs.
     */
    public void start() throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            this.out.display(CHAT_GREETING);
            while (this.chatMode != ChatMode.EXIT && scanner.hasNextLine()) {
                String message = scanner.nextLine();
                this.out.display(message);
                String key = this.toLowerCase(message);
                if (this.keywords.containsKey(key)) {
                    this.chatMode = this.keywords.get(key);
                } else if (this.chatMode != ChatMode.SILENT) {
                    String answer = this.bot.discuss(message);
                    this.out.display(answer);
                }
            }
        }
    }

    private String toLowerCase(String message) {
        return message.trim().toLowerCase();
    }

    /**
     * Chat modes enumeration
     */
    private enum ChatMode {
        EXIT, SILENT, SPEAKER
    }
}
