package ru.job4j.list;

/**
 * Очередь на основе связного списка.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <T> тип элементов очереди
 */
public class SimpleQueue<T> {
    SimpleLinkedList<T> queue;

    /**
     * Конструктор - создание объекта
     */
    public SimpleQueue() {
        this.queue = new SimpleLinkedList<>();
    }

    /**
     * Извлекает текущий элемент из очереди
     * @return текущий элемент
     */
    public T poll() {
        T item = this.queue.get(0);
        this.queue.remove(0);
        return item;
    }

    /**
     * Добавляет элемент в очередь
     * @param value добавляемый элемент
     */
    public void push(T value) {
        this.queue.add(value);
    }
}
