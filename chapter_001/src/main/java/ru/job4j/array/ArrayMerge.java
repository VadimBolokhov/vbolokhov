package ru.job4j.array;

/**
 * Тестовое задание "Объединение двух отсортированных массивов".
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ArrayMerge {
    /**
     * Объединяет два отсортированных массива в один отсортированный
     * @param first первый массив
     * @param second второй массив
     * @return отсортированный массив
     */
    public int[] merge(int[] first, int[] second) {
        int[] result = new int[first.length + second.length];
        int i = 0, j = 0, k = 0;
        while (i < first.length && j < second.length) {
            if (first[i] < second[j]) {
                result[k++] = first[i++];
            } else {
                result[k++] = second[j++];
            }
        }
        if (first.length > second.length) {
            System.arraycopy(first, i, result, k, result.length - k);
        } else {
            System.arraycopy(second, j, result, k, result.length - k);
        }
        return result;
    }
}
