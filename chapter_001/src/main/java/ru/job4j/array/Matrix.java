package ru.job4j.array;

/**
 * Таблица умножения.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Matrix {
    /**
     * Квадратная таблица умножения.
     * @param size размер таблицы
     * @return таблица умножения
     */
    public int[][] multiple(int size) {
        int[][] array = new int[size][size];
        for (int i = 0; i != size; i++) {
            for (int j = 0; j != size; j++) {
                array[i][j] = (i + 1) * (j + 1);
            }
        }
        return array;
    }
}
