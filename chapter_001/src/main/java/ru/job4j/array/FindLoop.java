package ru.job4j.array;

/**
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class FindLoop {
    /**
     * Проверяет наличие элемента в заданном массиве.
     *
     * @param data Массив целых чисел.
     * @param el Искомый элемент.
     * @return Номер элемента. Возвращает -1 если элемент не найден.
     */
    public int indexOf(int[] data, int el) {
        int rsl = -1;
        for (int index = 0; index != data.length; index++) {
            if (data[index] == el) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }
}
