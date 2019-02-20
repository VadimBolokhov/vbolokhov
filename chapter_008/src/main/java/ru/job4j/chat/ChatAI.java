package ru.job4j.chat;

/**
 * Chat bot interface.
 * @author Vadim Bolokhov
 */
public interface ChatAI {
    /**
     * Generates an answer to the specified message.
     * @param message message
     * @return answer
     */
    String discuss(String message);
}
