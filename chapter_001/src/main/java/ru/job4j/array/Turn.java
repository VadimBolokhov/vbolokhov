package ru.job4j.array;

/**
 * Используется для изменения порядка следования элементов массива.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Turn {
    /**
     * Инвертирует порядок элементов заданного массива.
     * @param array - задаваемый массив
     * @return перевернутый массив
     */
    public int[] back(int[] array) {
        for (int first = 0; first < array.length / 2; first++) {
            int cache = array[first];
            int second = array.length - 1 - first;
            array[first] = array[second];
            array[second] = cache;
        }
        return array;
    }
}
