package ru.job4j.switcher;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Свитчер потоков.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class Switcher {
    /** Количество вызовов метода append(), доступных потоку */
    private static final int MAX_APPENDS = 10;
    /** Строка */
    @GuardedBy("this")
    private StringBuffer sequence;
    /** Поток, которому разрешено добавлять символы в строку */
    @GuardedBy("this")
    private Thread activeThread;
    /** true - когда один из потоков начал добавлять символы в строку */
    private volatile boolean appendLocked = false;
    /** Обратный отсчёт вызовов метода append */
    @GuardedBy("this")
    private int counter = MAX_APPENDS;
    private ExecutorService threadPool;

    Switcher() {
        this.sequence = new StringBuffer();
        this.threadPool = Executors.newFixedThreadPool(2);
    }

    /**
     * Добавляет число в строку
     * @param number добавляемое число
     */
    public synchronized void append(int number) {
        try {
            this.appendOrWait(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void appendOrWait(int number) throws InterruptedException {
        this.setActiveThread();
        if (!this.appendLocked) {
            this.appendLocked = true;
        }
        this.sequence.append(number);
        if (--this.counter == 0) {
            this.switchThread();
        }
    }

    private synchronized void setActiveThread() throws InterruptedException {
        if (this.activeThread != Thread.currentThread()) {
            while (this.appendLocked) {
                wait();
            }
            this.activeThread = Thread.currentThread();
        }
    }

    private synchronized void switchThread() {
        this.counter = MAX_APPENDS;
        this.appendLocked = false;
        notify();
    }

    /**
     * Возвращает текущую последовательность символов
     * @return текущая строка
     */
    public synchronized String getSequence() {
        return this.sequence.toString();
    }

    /**
     * Запуск потоков, добавляющих числа в строку
     * @param period период добавления символов
     */
    public void runThreads(long period) {
        this.threadPool.submit(new ConcreteNumberGenerator(1, period));
        this.threadPool.submit(new ConcreteNumberGenerator(2, period));
    }

    /**
     * Завершение работы потоков
     */
    public void stopThreads() {
        this.threadPool.shutdown();
    }

    /**
     * Генератор конкретных чисел.
     */
    class ConcreteNumberGenerator implements Runnable {
        private final long period;
        private final int number;

        ConcreteNumberGenerator(int number, long period) {
            this.number = number;
            this.period = period;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(this.period);
                    append(number);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
