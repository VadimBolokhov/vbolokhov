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
        boolean result = true;
        HashMap<Character, Integer> map = new HashMap<>();
        Character letter = first.charAt(0);
        map.put(letter, 1);
            for (int i = 1; i < first.length(); i++) {
                letter = first.charAt(i);
                if (map.containsKey(letter)) {
                    map.replace(letter, map.get(letter) + 1);
                } else {
                    map.put(letter, 1);
                }
            }
            for (int i = 0; i < second.length(); i++) {
                letter = second.charAt(i);
                if (map.containsKey(letter)) {
                    map.replace(letter, map.get(letter) - 1);
                    if (map.get(letter) == 0) {
                        map.remove(letter);
                    }
                } else {
                    result = false;
                    break;
                }
            }
        return result && map.isEmpty();
    }
}
