package ru.job4j.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Simple socket client
 * @author Vadim Bolokhov
 */
public class BotClient {
    /** Client's socket */
    private final Socket socket;

    public BotClient(Socket socket) {
        this.socket = socket;
    }

    /**
     * Main cycle
     * @throws IOException if an I/O error occurs
     */
    public void start() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        Scanner console = new Scanner(System.in);
        String message = null;
        do {
            message = console.nextLine();
            out.println(message);
            for (message = in.readLine(); this.isInformative(message); message = in.readLine()) {
                System.out.println(message);
            }
        } while (message != null);
    }

    private boolean isInformative(String message) {
        return message != null && !message.isEmpty();
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 1111)) {
            new BotClient(socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
