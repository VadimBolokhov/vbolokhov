package ru.job4j.pool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Пул потоков.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class ThreadPool {
    /** Количество потоков в пуле */
    private final int concurrency;
    /** Список потоков */
    private final List<Worker> threads;
    /** Очередь задач для выполнения */
    @GuardedBy("this")
    private final Queue<Work> tasks;

    /**
     * Конструктор - создание пула в соответствии с количествоя ядер в системе
     */
    ThreadPool() {
        this(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Конструктор - создание объекта с заданным параметром
     * @param concurrency количество потоков
     */
    ThreadPool(final int concurrency) {
        this.concurrency = concurrency;
        this.threads = new LinkedList<>();
        this.tasks = new LinkedList<>();
        this.initThreads();
    }

    /**
     * Создание и запуск потоков
     */
    private void initThreads() {
        for (int i = 0; i < this.concurrency; i++) {
            Worker thread = new Worker();
            thread.start();
            this.threads.add(thread);
        }
    }

    /**
     * Добавляет задачу
     * @param work добавляемая задача
     */
    public synchronized void add(Work work) {
            tasks.offer(work);
            notify();
    }

    /**
     * Класс потока
     */
    private class Worker extends Thread {
        Work work;

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                synchronized (ThreadPool.this) {
                    while (tasks.isEmpty()) {
                        try {
                            ThreadPool.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    this.work = tasks.poll();
                }
                this.work.run();
            }
        }
    }

}
