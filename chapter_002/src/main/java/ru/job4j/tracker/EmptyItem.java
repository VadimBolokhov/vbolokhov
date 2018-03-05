package ru.job4j.tracker;

/**
 * Класс, на случай если заявка не найдена.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class EmptyItem extends Item {
    private static final String EMPTY = "<Empty Item>";
    private String message;

    /**
     * Конструктор - создание нового объекта
     */
    EmptyItem() {
        this("Заявки не существует");
    }

    /**
     * Конструктор - создание нового объекта с заданным параметром
     * @param message сообщение для пользователя
     */
    EmptyItem(String message) {
        this.message = message;
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return EMPTY;
    }

    @Override
    public String getName() {
        return EMPTY;
    }

    @Override
    public String getDesc() {
        return EMPTY;
    }

    @Override
    public String toString() {
        return message;
    }
}
