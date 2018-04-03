package ru.job4j.generic;

/**
 * Хранилище данных пользователей.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UserStore extends AbstractStore<User> {
    /**
     * Конструктор - создание хранилища заданного размера
     * @param size количество элементов
     */
    public UserStore(int size) {
        super(size);
    }


}
