package ru.job4j.synchonize;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Count Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class CountTest {

    /**
     * Класс описывает нить со счётчиком.
     */
    private class ThreadCount extends Thread {
        private final Count count;

        private ThreadCount(final Count count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final Count count = new Count();
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(2));
    }
}