package ru.job4j.tracker;

/**
 * Интерфейс ввода.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public interface Input {
    /**
     * Осуществляет диалог программы с пользователем
     * @param quesiton сообщение для пользователя
     * @return ответ пользователя
     */
    String ask(String quesiton);
}
