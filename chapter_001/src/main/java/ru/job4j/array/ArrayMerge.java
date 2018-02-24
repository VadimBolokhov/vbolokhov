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
        for (; (i < first.length) && (j < second.length); k++) {
            if (first[i] < second[j]) {
                result[k] = first[i];
                i++;
            } else {
                result[k] = second[j];
                j++;
            }
        }
        while (i < first.length) {
            result[k++] = first[i++];
        }
        while (j < second.length) {
            result[k++] = second[j++];
        }
        return result;
    }
}
