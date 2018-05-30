package ru.job4j.lock;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Механизм блокировок.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class SimpleLock {
    @GuardedBy("this")
    private boolean locked = false;
    /** Поток, захвативший лок */
    private Thread lockedBy;

    /**
     * Захватывает лок (если свободен)
     */
    public synchronized void lock() {
        while (this.locked) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.locked = true;
        this.lockedBy = Thread.currentThread();
    }

    /**
     * Освобождает лок
     */
    public synchronized void unlock() {
        if (Thread.currentThread() == this.lockedBy) {
            this.locked = false;
            notify();
        }
    }

    /**
     * Проверка свободен ли лок
     * @return {@code true} - если лок занят, {@code false} - в противном случае
     */
    public synchronized boolean isLocked() {
        return this.locked;
    }
}
