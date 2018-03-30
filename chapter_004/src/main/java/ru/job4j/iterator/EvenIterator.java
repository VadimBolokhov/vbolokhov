package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для чётных чисел.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class EvenIterator implements Iterator {
    /** Целочисленный массив */
    private final int[] numbers;
    /** Текущая позиция итератора */
    private int position;
    /** Позиция следующего четного элемента, включая позицию итератора */
    private int nextEven;

    /**
     * Конструктор - создание итератора с заданным параметром
     * @param numbers целочисленный массив
     */
    public EvenIterator(final int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * Поиск следующего четного числа в массиве, начиная текущей позиции итератора
     * @return true - если элемент найден, false - если элемент не найден
     */
    private boolean setNextEven() {
        this.nextEven = -1;
        for (int i = this.position; i < this.numbers.length; i++) {
            if (this.numbers[i] % 2 == 0) {
                this.nextEven = i;
                break;
            }
        }
        return this.nextEven >= 0;
    }

    @Override
    public boolean hasNext() {
        return setNextEven();
    }

    @Override
    public Object next() {
        int result;
        try {
            this.setNextEven();
            result = this.numbers[this.nextEven];
            this.position = this.nextEven + 1;
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        return result;
    }
}
