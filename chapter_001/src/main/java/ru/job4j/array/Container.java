package ru.job4j.array;

/**
 * Тестовое задание "Проверка, что одно слово находится в другом слове"
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Container {
    /**
     * Проверяет, что слово String находится в другом слове String
     * @param origin слово, в котором ищем другое слово
     * @param sub слово, которое ищем в другом слове
     * @return true - если искомое слово найдено, false - в протисном случае
     */
    public boolean contains(String origin, String sub) {
        char[] word = origin.toCharArray();
        char[] subWord = sub.toCharArray();
        boolean result = false;
        for (int i = 0; i <= word.length - subWord.length; i++) {
            result = true;
            for (int j = 0; j < subWord.length; j++) {
                if (word[j + i] != subWord[j]) {
                    result = false;
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }
}
