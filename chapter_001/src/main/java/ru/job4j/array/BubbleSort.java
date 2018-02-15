package ru.job4j.array;

/**
 * Сортировка пузырьком.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class BubbleSort {
    /**
     * Сортирует заданный массив.
     * @param array массив для сортировки
     * @return упорядоченный массив
     */
    public int[] sort(int[] array) {
        for (int run = 1; run != array.length; run++) {
            for (int i = 0; i != array.length - run; i++) {
                if (array[i] > array[i + 1]) {
                    int cache = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = cache;
                }
            }
        }
        return array;
    }
}
