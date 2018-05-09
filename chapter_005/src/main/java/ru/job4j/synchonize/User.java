package ru.job4j.synchonize;

/**
 * Класс пользователя.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class User {
    /** Идентификатор */
    private int id;
    /** Сумма денег на счёте */
    private int amount;

    /**
     * Конструктор - создание объекта с заданными параметрами
     * @param id идентификатор пользователя
     * @param amount сумма денег на счёте пользователя
     */
    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    /**
     * Возвращает ID пользователя
     * @return идентификатор
     */
    public int getId() {
        return this.id;
    }

    /**
     * Возвращает сумму денег на счёте пользоваетля
     * @return сумма денег на счёте
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Задаёт сумму денег на счёте польователя
     * @param amount сумма
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Добавляет заданную сумму к текущему счёту пользователя
     * @param amount добавляемая сумма
     */
    public void addValue(int amount) {
        this.amount += amount;
    }
}
