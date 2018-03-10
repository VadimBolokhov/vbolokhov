package ru.job4j.tracker;

/**
 * Исключение выбрасывается при выборе несуществуюзего пункта меню.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class MenuOutException extends RuntimeException {
    /**
     * Конструктор - создание объекта с заданным параметром
     * @param msg сообщение об ошибке
     */
    MenuOutException(String msg) {
        super(msg);
    }
}
