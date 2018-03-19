package ru.job4j.search;

/**
 * Класс пользователя.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class User {
    private int id;
    private String name;
    private String city;

    /**
     * Конструктор - создание объекта с заданными параметрами
     * @param id идентификатор
     * @param name имя
     * @param city город
     */
    public User(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }
}
