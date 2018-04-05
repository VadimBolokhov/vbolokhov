package ru.job4j.list;

/**
 * Интерфейс коллекции.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public interface SimpleContainer<E> extends Iterable<E> {
    /**
     * Добавляет элемент в коллекцию
     * @param e добавляемый элемент
     */
    void add(E e);

    /**
     * Извлекает элемент коллекции по заданному индексу
     * @param index заданный индекс
     * @return извлекаемый элемент
     */
    E get(int index);
}
