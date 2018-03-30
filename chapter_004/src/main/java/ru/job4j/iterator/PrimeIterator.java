package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для простых чисел.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class PrimeIterator implements Iterator {
    /** Целочисленный массив */
    private final int[] numbers;
    /** Текущая позиция итератора */
    private int position;
    /** Позиция следующего простого числа, включая позицию итератора */
    private int nextPrime;

    /**
     * Конструктор - создание итератора с заданным параметром
     * @param numbers целочисленный массив
     */
    public PrimeIterator(final int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * Проверка, является ли заданное число простым
     * @param number заданное число
     * @return true - если число простое, false - в протисвном случае
     */
    private boolean isPrime(int number) {
        boolean prime = true;
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                prime = false;
                break;
            }
        }
        return number != 1 && prime;
    }

    /**
     * Поиск следующего простого числа в массиве, начиная с текущей позиции итератора
     * @return true - если элемент найден, false - если элемент не найден
     */
    private boolean setNextPrime() {
        this.nextPrime = -1;
        for (int i = this.position; i < this.numbers.length; i++) {
            if (this.isPrime(this.numbers[i])) {
                this.nextPrime = i;
                break;
            }
        }
        return this.nextPrime >= 0;
    }

    @Override
    public boolean hasNext() {
        return setNextPrime();
    }

    @Override
    public Object next() {
        int result;
        try {
            this.setNextPrime();
            result = this.numbers[this.nextPrime];
            this.position = this.nextPrime + 1;
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
        return result;
    }
}
