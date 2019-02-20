package ru.job4j.chat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Console chat test.
 * @author Vadim Bolokhov
 */
public class StartChatTest {
    private InputStream standardIn = System.in;
    private PrintStream standardOut = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void revertOut() {
        System.setOut(this.standardOut);
        System.setIn(this.standardIn);
    }

    @Test
    public void whenUserTypesAMessageShouldReturnRandomPhrase() throws IOException {
        String messages = this.setMessageSequence("some message");
        ByteArrayInputStream in = new ByteArrayInputStream(messages.getBytes());
        System.setIn(in);
        List<String> expectedAnswers = this.getExpectedStrings();

        StartChat.main(new String[0]);

        List<String> result = this.getResultList();
        assertTrue(expectedAnswers.contains(result.get(2)));
    }

    private List<String> getResultList() {
        String result = new String(this.out.toByteArray());
        String[] answers = result.split(System.lineSeparator());
        return Arrays.asList(answers);
    }

    private String setMessageSequence(String... sequence) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        Stream.of(sequence).forEach(sj::add);
        sj.add(Chat.EXIT_WORD);
        return sj.toString();
    }

    private List<String> getExpectedStrings() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(StartChat.BOT_INPUT));
        String[] strings = new String(bytes).split(System.lineSeparator());
        return Arrays.asList(strings);
    }

    @Test
    public void whenUserTypesStopThenChatBotShouldBecameSilent() {
        String messages = this.setMessageSequence(Chat.SUSPEND_WORD, "some message");
        ByteArrayInputStream in = new ByteArrayInputStream(messages.getBytes());
        System.setIn(in);
        int expectedChatLines = 4; // to make sure there are no bot messages

        StartChat.main(new String[0]);

        List<String> result = this.getResultList();
        assertThat(result.size(), is(expectedChatLines));
    }

    @Test
    public void whenUserTypesResumeThenBotShouldAnswer() throws IOException {
        String messages = this.setMessageSequence(Chat.SUSPEND_WORD, "some message",
                Chat.RESUME_WORD, "another message");
        ByteArrayInputStream in = new ByteArrayInputStream(messages.getBytes());
        System.setIn(in);
        List<String> expectedStrings = this.getExpectedStrings();

        StartChat.main(new String[0]);

        List<String> result = this.getResultList();
        int answerIndex = result.size() - 2;
        String answer = result.get(answerIndex);
        assertTrue(expectedStrings.contains(answer));
    }
}