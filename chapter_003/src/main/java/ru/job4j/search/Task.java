package ru.job4j.search;

/**
 * Задача.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Task {
    /** Описание */
    private String desc;
    /** Приоритет */
    private int priority;

    /**
     * Конструктор - создание задачи с заданными параметрами
     * @param desc описание задачи
     * @param priority приоритет
     */
    public Task(String desc, int priority) {
        this.desc = desc;
        this.priority = priority;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getPriority() {
        return this.priority;
    }
}
