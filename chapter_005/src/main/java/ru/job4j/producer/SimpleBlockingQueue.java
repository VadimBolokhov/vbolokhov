package ru.job4j.producer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Блокирующая очередь, ограниченная по размеру.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <T> тип элементов коллекции
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    /** Хранилище элементов очереди */
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    /** Максимальный размер очереди */
    private final int maxSize;

    /**
     * Конструктор - создание объекта с заданным параметром
     * @param size максимальный размер очереди (должен быть больше нуля)
     */
    SimpleBlockingQueue(final int size) {
        this.maxSize = size;
    }

    /**
     * Добавляет элемент в очередь
     * @param value добавляемый элемент
     */
    public synchronized void offer(T value) {
        while (this.queue.size() >= this.maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.queue.offer(value);
        notify();
    }

    /**
     * Извлекает элемент из очереди
     * @return извлекаемый элемент
     */
    public synchronized T poll() {
        while (this.queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
        return this.queue.poll();
    }
}
