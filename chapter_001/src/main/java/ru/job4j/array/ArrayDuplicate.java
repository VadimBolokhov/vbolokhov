package ru.job4j.array;

import java.util.Arrays;

/**
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicate {
    /**
     * Удаляет из массива все дубликаты строк.
     * @param array задаваемый массив
     * @return массив без повторяющихся элементов
     */
    public String[] remove(String[] array) {
        int duplicates = 0;
        int last = array.length - 1;
        for (int i = 0; i < last; i++) {
            for (int j = last; j > i; j--) {
                if (array[i].equals(array[j])) {
                    for (int k = j; k < last; k++) {
                        String cache = array[k];
                        array[k] = array[k + 1];
                        array[k + 1] = cache;
                    }
                    duplicates++;
                    last = array.length - duplicates - 1;
                }
            }
        }
        return Arrays.copyOf(array, array.length - duplicates);
    }
}
