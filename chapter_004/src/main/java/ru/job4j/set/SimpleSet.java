package ru.job4j.set;

import ru.job4j.list.SimpleList;

import java.util.Iterator;

/**
 * Множество на базе динамического массива.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <E> тип элементов множества
 */
public class SimpleSet<E> implements Iterable<E> {
    /** Хранилище элементов коллекции */
    private SimpleList<E> list;

    /**
     * Конструктор - создание объекта
     */
    public SimpleSet() {
        this.list = new SimpleList<>();
    }

    /**
     * Добавляет элемент в коллекцию
     * @param value добавляемый элемент
     */
    public void add(E value) {
        boolean unique = true;
        for (E item : this.list) {
            if (item.equals(value)) {
                unique = false;
                break;
            }
        }
        if (unique) {
            this.list.add(value);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return this.list.iterator();
    }
}
