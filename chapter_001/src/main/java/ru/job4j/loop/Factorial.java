package ru.job4j.loop;

/**
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Factorial {
    /**
     * Вычисляет факториал заданного числа.
     * @param n Число, факториал которого необходимо вычислить.
     * @return Результат вычисления факториала.
     */
    public int calc(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
