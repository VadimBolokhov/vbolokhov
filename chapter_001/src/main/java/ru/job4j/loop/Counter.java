package ru.job4j.loop;

/**
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Counter {
    /**
     * Вычисляет сумму всех четных чисел в заданном диапазоне.
     *
     * @param start Начальное значение.
     * @param finish Конечное значение.
     * @return Сумма четных чисел.
     */
    public int add(int start, int finish) {
        int result = 0;
        for (int i = start; i <= finish; i++) {
            if (i % 2 == 0) {
                result += i;
            }
        }
        return result;
    }
}
