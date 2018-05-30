package ru.job4j.lock;

import org.junit.Test;
import ru.job4j.producer.SimpleBlockingQueue;

import static java.lang.Thread.sleep;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * ThreadPool Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleLockTest {
    private class SimpleThread extends Thread {
        SimpleLock sLock;

        SimpleThread(SimpleLock sl) {
            this.sLock = sl;
        }

        @Override
        public void run() {
            sLock.lock();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sLock.unlock();
        }
    }

    @Test
    public void whenLockUnlockedReturnFalse() throws InterruptedException {
        SimpleLock lock = new SimpleLock();
        SimpleThread st = new SimpleThread(lock);
        assertThat(lock.isLocked(), is(false));
        st.start();
        sleep(50);
        assertThat(lock.isLocked(), is(true));
        lock.unlock();
        assertThat(lock.isLocked(), is(true));
        st.join();
        assertThat(lock.isLocked(), is(false));
    }
}