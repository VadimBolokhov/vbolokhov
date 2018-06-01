package ru.job4j.compare;

import java.util.HashMap;

/**
 * Сравнение строк
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class StringComparer  {
    /**
     * Сравнивает строки как наборы символов
     * @param first первая сравниваемая строка
     * @param second вторая сравниваемая строка
     * @return {@code true} - если строки содержат одинаковые наборы символов,
     * {@code false} - в противном случае
     */
    public boolean compareStrings(String first, String second) {
        boolean result = false;
        if (first.length() == second.length()) {
            result = this.compareChars(first, second);
        }
        return result;
    }

    private boolean compareChars(String first, String second) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < first.length(); i++) {
            map.merge(first.charAt(i), 1, (v1, v2) ->
                    v1 + v2 == 0 ? null : v1 + v2);
            map.merge(second.charAt(i), -1, (v1, v2) ->
                    v1 + v2 == 0 ? null : v1 + v2);
        }
        return map.isEmpty();
    }
}
