package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для двумерных массивов.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class MatrixIterator implements Iterator {
    /** Двумерный массив */
    private int[][] array;
    /** Текущая строка массива */
    private int raw;
    /** Текущая ячейка массива */
    private int col;

    /**
     * Конструктор - создание объекта с заданным параметром
     * @param array двумерный массив
     */
    public MatrixIterator(int[][] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return raw != this.array.length - 1 || col != this.array[raw].length;
    }

    @Override
    public Object next() {
        try {
            if (this.col == this.array[this.raw].length) {
                this.col = 0;
                raw++;
            }
            return this.array[raw][col++];
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
    }
}
