package ru.job4j.list;

/**
 * Стэк на основе связного списка.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <T> тип элементов стэка
 */
public class SimpleStack<T> {
    /** Связный список для стэка */
    SimpleLinkedList<T> stack;

    /**
     * Конструктор - создание объекта
     */
    public SimpleStack() {
        this.stack = new SimpleLinkedList<>();
    }

    /**
     * Извлекает текущий элемент из стэка
     * @return текущий элемент
     */
    public T poll() {
        T item = this.stack.getLast();
        this.stack.removeLast();
        return item;
    }

    /**
     * Добавляет элемент в стэк
     * @param value добавляемый элемент
     */
    public void push(T value) {
        this.stack.add(value);
    }
}
