package ru.job4j.tracker;

import java.util.List;

/**
 * Интерфейс ввода.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public interface Input {
    /**
     * Осуществляет диалог программы с пользователем
     * @param question сообщение для пользователя
     * @return ответ пользователя
     */
    String ask(String question);

    /**
     * Осуществляет диалог программы с пользователем, а также проверяет
     * попадает ли ответ пользователя в заданный диапазон значений
     * @param question сообщение для пользователя
     * @param range диапазон для проверки
     * @return ответ пользователя
     */
    int ask(String question, List<Integer> range);
}
