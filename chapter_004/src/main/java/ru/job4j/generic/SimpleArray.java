package ru.job4j.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Коллекция на основе массива.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SimpleArray<T> implements Iterable<T> {
    /** Массив коллекции */
    private Object[] array;
    /** Номер ячейки, куда будет довавлен следующий элемент */
    private int index = 0;

    /**
     * Конструктор - создание массива заданного размера
     * @param size размер массива
     */
    public SimpleArray(int size) {
        this.array = new Object[size];
    }

    /**
     * Добавляет элемент в коллекцию
     * @param model заданный элемент
     */
    public void add(T model) {
        this.array[this.index++] = model;
    }

    /**
     * Замена элемента коллекции по заданному индексу
     * @param index заданный индекс
     * @param model новый элемент
     */
    public void set(int index, T model) {
        this.array[index] = model;
    }

    /**
     * Извлекает элемент коллекции по заданному индексу
     * @param index заданный индекс
     * @return
     */
    public T get(int index) {
        return (T) this.array[index];
    }

    /**
     * Удаляет элемент коллекции по заданному индексу
     * @param index заданный индекс
     */
    public void delete(int index) {
        int items = this.array.length - index - 1;
        System.arraycopy(this.array, index + 1, this.array, index, items);
        this.array[--this.index] = null;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int position = 0;

            @Override
            public boolean hasNext() {
                return this.position < SimpleArray.this.array.length;
            }

            @Override
            public T next() {
                try {
                    return (T) SimpleArray.this.array[this.position++];
                } catch (IndexOutOfBoundsException e) {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
