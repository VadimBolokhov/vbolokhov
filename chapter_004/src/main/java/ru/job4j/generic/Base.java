package ru.job4j.generic;

/**
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public abstract class Base {
    private final String id;

    /**
     * Конструктор - создание объекта с заданным {@code id}
     * @param id идентификатор
     */
    public Base(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
