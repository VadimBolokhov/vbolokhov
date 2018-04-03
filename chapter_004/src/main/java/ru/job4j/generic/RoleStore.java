package ru.job4j.generic;

/**
 * Хранилище должностей.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class RoleStore extends AbstractStore<Role> {
    /**
     * Конструктор - создание хранилища заданного размера
     * @param size количество элементов
     */
    public RoleStore(int size) {
        super(size);
    }
}
