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
    /** When true the bot doesn't answer */
    private boolean isSilentMode = false;
    /** Leave chat */
    private boolean exit = false;
    /** Chat reserved words */
    private Map<String, Runnable> keywords = new HashMap<>();

    public Chat(ChatOut out, ChatAI bot) {
        this.out = out;
        this.bot = bot;
        this.addKeywords();
    }

    private void addKeywords() {
        this.keywords.put(EXIT_WORD, () -> this.exit = true);
        this.keywords.put(SUSPEND_WORD, () -> this.isSilentMode = true);
        this.keywords.put(RESUME_WORD, () -> this.isSilentMode = false);
    }

    /**
     * Main cycle. Provides interaction between user and chat bot.
     * @throws IOException If an I/O error occurs.
     */
    public void start() throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            this.out.display(CHAT_GREETING);
            while (!this.exit && scanner.hasNextLine()) {
                String message = scanner.nextLine();
                this.out.display(message);
                String key = this.toLowerCase(message);
                if (this.keywords.containsKey(key)) {
                    this.keywords.get(key).run();
                } else if (!this.isSilentMode) {
                    String answer = this.bot.discuss(message);
                    this.out.display(answer);
                }
            }
        }
    }

    private String toLowerCase(String message) {
        return message.trim().toLowerCase();
    }
}
