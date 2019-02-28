package ru.job4j.bot;

import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * BotClient test.
 * @author Vadim Bolokhov
 */
public class BotClientTest {
    private static final String LN = System.lineSeparator();
    private final InputStream standardIn = System.in;
    private final PrintStream standardOut = System.out;

    @After
    public void revertSystemIO() {
        System.setOut(this.standardOut);
        System.setIn(this.standardIn);
    }

    @Test
    public void whenSendHelloThenAnswerShouldBeDisplayedInConsole() throws IOException {
        ByteArrayOutputStream out = this.setSystemIO("Hello oracle");
        String botAnswer = BotServer.SERVER_GREETING;
        Socket socket = this.mockSocket(botAnswer);
        String expected = String.format("%s%s", botAnswer, LN);

        new BotClient(socket).start();

        String result = new String(out.toByteArray());
        assertThat(result, is(expected));

    }

    private ByteArrayOutputStream setSystemIO(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        return out;
    }

    private Socket mockSocket(String botAnswer) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream socketIn = new ByteArrayInputStream(botAnswer.getBytes());
        ByteArrayOutputStream socketOut = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(socketIn);
        when(socket.getOutputStream()).thenReturn(socketOut);
        return socket;
    }
}