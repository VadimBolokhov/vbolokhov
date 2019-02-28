package ru.job4j.bot;

import com.google.common.base.Joiner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * BotServer test.
 * @author Vadim Bolokhov
 */
public class BotServerTest {
    private static final String LN = System.lineSeparator();
    private final PrintStream standardOut = System.out;

    /**
     * Prevents messages from being displayed on the console
     */
    @Before
    public void setSystemOut() {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    @After
    public void revertSystemOut() {
        System.setOut(this.standardOut);
    }

    @Test
    public void whenAskQuestionThenChooseRandom() throws IOException {
        this.testServer("exit", "");
    }

    private void testServer(String input, String expected) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        BotServer server = new BotServer(socket);

        server.start();

        assertThat(out.toString(), is(expected));
    }

    @Test
    public void whenAskHelloThenShouldReturnGreeting() throws IOException {
        String input = Joiner.on(System.lineSeparator()).join(
                "Hello oracle",
                "exit"
        );
        String expected = Joiner.on(LN).join(
                BotServer.SERVER_GREETING, "", ""
        );
        this.testServer(input, expected);
    }

    @Test
    public void whenAskUnknownPhraseThenDontUnderstand() throws IOException {
        String input = Joiner.on(System.lineSeparator()).join(
                "test",
                "exit"
        );
        String expected = Joiner.on(LN).join(
                "I don't understand", "", ""
        );
        this.testServer(input, expected);
    }
}