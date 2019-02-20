package ru.job4j.chat;

/**
 * Console output.
 * @author Vadim Bolokhov
 */
public class ConsoleChatOut implements ChatOut {

    @Override
    public void display(String message) {
        System.out.println(message);
    }
}
