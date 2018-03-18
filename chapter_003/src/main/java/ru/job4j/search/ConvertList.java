package ru.job4j.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Конвертация двумерного массива в ArrayList и наоборот.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ConvertList {
    /**
     * Конвертирует двумерный массив в свисок ArrayList
     * @param array заданный двумерный массив
     * @return список ArrayList
     */
    public List<Integer> toList(int[][] array) {
        List<Integer> result = new ArrayList<>();
        for (int[] i : array) {
            for (int j : i) {
                result.add(j);
            }
        }
        return result;
    }

    /**
     * Конвертирует список ArrayList в двумерный массив
     * @param list список
     * @param rows требуемое количество строк массива
     * @return двумерый массив
     */
    public int[][] toArray(List<Integer> list, int rows) {
        int columns = (list.size() + rows - 1) / rows;
        int[][] result = new int[rows][columns];
        Iterator<Integer> iterator = list.iterator();
        if (list.size() % rows != 0) {
            Arrays.fill(result[rows - 1], result.length - list.size() % rows, result.length - 1, 0);
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns && iterator.hasNext(); j++) {
                result[i][j] = iterator.next();
            }
        }
        return result;
    }
}
