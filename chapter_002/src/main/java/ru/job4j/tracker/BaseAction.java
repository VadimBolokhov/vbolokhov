package ru.job4j.tracker;

/**
 * Абстрактный класс для пунктов меню.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public abstract class BaseAction implements UserAction {
    /** Ключ пункта меню */
    private final int key;
    /** Название пункта меню */
    private final String name;

    /**
     * Конструктор - создание объекта с заданными параметрами
     * @param key ключ пункта меню
     * @param name название пункта меню
     */
    protected BaseAction(final int key, final String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int key() {
        return this.key;
    }

    @Override
    public String info() {
        return this.name;
    }
}
