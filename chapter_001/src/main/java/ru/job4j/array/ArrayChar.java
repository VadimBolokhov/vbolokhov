package ru.job4j.array;

/**
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ArrayChar {
    private char[] data;

    public ArrayChar(String line) {
        this.data = line.toCharArray();
    }

    /**
     * Проверяет, что слово начинается с префикса.
     * @param prefix префикс
     * @return возвращает true - если слово начинается с префикса, false - в приотивном случае
     */
    public boolean startWith(String prefix) {
        boolean result = true;
        char[] value = prefix.toCharArray();
        int length = value.length <= this.data.length ? value.length : this.data.length;
        for (int i = 0; i < length; i++) {
            if (this.data[i] != value[i]) {
                result = false;
                break;
            }
        }
        return result;
    }
}
