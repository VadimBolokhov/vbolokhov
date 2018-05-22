package ru.job4j.producer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * SimpleBlockingQueue Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleBlockingQueueTest {
    private List<Integer> result = new ArrayList<>();

    private class Producer extends Thread {
        private SimpleBlockingQueue<Integer> queue;

        Producer(SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                this.queue.offer(i);
            }
        }
    }

    private class Consumer extends Thread {
        private SimpleBlockingQueue<Integer> queue;

        Consumer(SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                result.add(this.queue.poll());
            }
        }
    }

    @Test
    public void whenOffer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Producer(queue);
        Thread consumer = new Consumer(queue);
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4);
        assertThat(result, is(expected));
        result.clear();
    }
}