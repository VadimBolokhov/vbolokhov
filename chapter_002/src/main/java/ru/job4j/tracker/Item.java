package ru.job4j.tracker;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Класс заявки.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Item {
    /** Id заявки */
    private String id;
    /** Имя */
    private String name;
    /** Описание */
    private String desc;
    /** Дата создания */
    private long created;
    /** Список комментариев */
    private List<String> comments;

    /**
     * Конструктор - создание нового объекта
     */
    Item() {
        this("Default Item", "", 0);
        this.id = "";
    }

    /**
     * Конструктор - создание нового объекта с заданными параметрами
     * @param name имя
     * @param desc опсиание
     */
    Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * Конструктор - создание нового объекта с заданными параметрами
     * @param name имя
     * @param desc описание
     * @param created дата создания
     */
    Item(String name, String desc, long created) {
        this(name, desc);
        this.created = created;
    }

    /**
     * Задаёт Id заявки
     * @param id идентификатор
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Возвращает id заявки
     * @return id заявки
     */
    public String getId() {
        return this.id;
    }

    /**
     * Возвращает имя
     * @return имя
     */
    public String getName() {
        return this.name;
    }

    /**
     * Описание заявки
     * @return описание
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * Переопределяет toString()
     * @return поля заявки: имя, опмсание, id
     */
    @Override
    public String toString() {
        String description = new StringJoiner(System.lineSeparator())
                .add("Name: " + this.name)
                .add("Description: " + this.desc)
                .add("id: " + this.id)
                .toString();
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(id, item.id)
                && Objects.equals(name, item.name)
                && Objects.equals(desc, item.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc);
    }
}
