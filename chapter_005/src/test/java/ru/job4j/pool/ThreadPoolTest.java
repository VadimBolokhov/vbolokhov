package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * ThreadPool Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ThreadPoolTest {
    private volatile int count;
    private final Object lock = new Object();

    @Test
    public void whenAddWorkThenWorkAddedToQueue()throws InterruptedException {
        Work work = new Work(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    synchronized (lock) {
                        count++;
                    }
                }
            }
        });
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 5; i++) {
            pool.add(work);
        }
        Thread.sleep(1);
        assertThat(this.count, is(25));
    }
}