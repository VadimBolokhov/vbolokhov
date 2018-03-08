package ru.job4j.tracker;

/**
 * Интерфейс пунктов меню.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public interface UserAction {
    /**
     * Ключ пункта меню
     */
    int key();

    /**
     * Выполняет операцию, за которую отвечает данный пункт
     */
    void execute(Input input, Tracker tracker);

    /**
     * Название пункта меню
     */
    String info();
}
