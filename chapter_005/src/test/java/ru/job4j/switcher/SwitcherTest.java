package ru.job4j.switcher;

import org.junit.Ignore;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Switcher Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@Ignore
public class SwitcherTest {
    @Test
    public void whenStartThreadsThenReturnStringWithOnesAndTwos() throws InterruptedException {
        Switcher s = new Switcher();
        s.runThreads(10);
        sleep(200);
        s.stopThreads();
        assertThat(s.getSequence().contains("1111111111"), is(true));
        assertThat(s.getSequence().contains("2222222222"), is(true));
    }
}