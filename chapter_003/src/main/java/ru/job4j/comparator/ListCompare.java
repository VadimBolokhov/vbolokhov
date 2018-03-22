package ru.job4j.comparator;

import java.util.*;

/**
 * Компаратор для строк.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ListCompare implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        int result = 0;
        List<Character> lList = this.stringToList(left);
        List<Character> rList = this.stringToList(right);
        int steps = Math.min(lList.size(), rList.size());
        for (int i = 0; i < steps; i++) {
            if (lList.get(i) != rList.get(i)) {
                result = Character.compare(lList.get(i), rList.get(i));
                break;
            }
        }
        return result != 0 ? result : Integer.compare(lList.size(), rList.size());
    }

    /**
     * Конвертирует строку в список символов
     * @param str строка
     * @return список символов
     */
    private List<Character> stringToList(String str) {
        List<Character> result = new ArrayList<>();
        for (char c : str.toCharArray()) {
            result.add(c);
        }
        return result;
    }
}
