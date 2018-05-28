package ru.job4j.pool;

/**
 * Задача.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Work {
    private Runnable runnable;

    Work(Runnable runnable) {
        this.runnable = runnable;
    }

    public void run() {
        this.runnable.run();
    }
}
