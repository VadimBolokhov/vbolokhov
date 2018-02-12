package ru.job4j.max;

/**
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Max {
    /**
     * Возвращает наибольшее из двух целых чисел.
     * @param first - первое сравниваемое число.
     * @param second - второе сравниваемое число.
     * @return наибольшее из двух.
     */
    public int max(int first, int second) {
        return first >= second ? first : second;
    }

    /**
     * Возвращает наибольшее из трех целых чисел.
     * @param first - первое сравниваемое число.
     * @param second - второе сравниваемое число.
     * @param third - третье сравниваемое число.
     * @return наибольшее из трех.
     */
    public int max(int first, int second, int third) {
        return max(first, max(second, third));
    }
}
