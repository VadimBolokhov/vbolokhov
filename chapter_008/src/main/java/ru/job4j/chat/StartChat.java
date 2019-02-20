package ru.job4j.chat;

import java.io.File;
import java.io.IOException;

/**
 * Chat starter.
 * @author Vadim Bolokhov
 */
public class StartChat {
    /** Output file */
    public static final String OUTPUT_FILE = "chat.log";
    /** Chat log */
    public static final String BOT_INPUT = "./src/main/resources/bot.txt";

    public static void main(String[] args) {
        ConsoleChatOut consoleOut = new ConsoleChatOut();
        File output = new File(OUTPUT_FILE);
        if (output.getParentFile() != null) {
            output.getParentFile().mkdirs();
        }
        try (FileChatOut fileOut = new FileChatOut(OUTPUT_FILE)) {
            MultiOut out = new MultiOut();
            out.addOut(consoleOut);
            out.addOut(fileOut);
            ChatAI bot = new FileBasedAI(BOT_INPUT);
            Chat chat = new Chat(out, bot);
            chat.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
