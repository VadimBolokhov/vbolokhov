package ru.job4j.array;

/**
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Square {
    /**
     * Возвращает мыссив заполненный, квадратами чисел от 1 до bound.
     *
     * @param bound Длина массива.
     * @return Массив.
     */
    public int[] calculate(int bound) {
        int[] rst = new int[bound];
        for (int i = 0; i != bound; i++) {
            rst[i] = (i + 1) * (i + 1);
        }
        return rst;
    }
}
