package ru.job4j.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple 'Wise Oracle' server application.
 * @author Vadim Bolokhov
 */
public class BotServer {
    /** Default bot's greeting */
    public static final String SERVER_GREETING = "Hello, dear friend, I'm an oracle.";
    /** Main socket to deal with */
    private final Socket socket;

    public BotServer(Socket socket) {
        this.socket = socket;
    }

    /**
     * Main cycle
     * @throws IOException if an I/O error occurs
     */
    public void start() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        String ask = null;
        do {
            System.out.println("wait command ...");
            ask = in.readLine();
            System.out.println(ask);
            if ("Hello oracle".equals(ask)) {
                out.println("Hello, dear friend, I'm an oracle.");
                out.println();
            } else if (!"exit".equals(ask)) {
                out.println("I don't understand");
                out.println();
            }
        } while (!"exit".equals(ask));
    }

    public static void main(String[] args) {
        try (final Socket socket = new ServerSocket(1111).accept()) {
            new BotServer(socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
