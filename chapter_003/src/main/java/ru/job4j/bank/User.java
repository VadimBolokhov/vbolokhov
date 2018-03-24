package ru.job4j.bank;

import java.util.Objects;

/**
 * Класс клиента банка (для тестового задания "Банковские переводы").
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class User {
    /** Имя клиента */
    private String name;
    /** Паспортные данные клиента */
    private String passport;

    /**
     * Конструктор - создание объекта с заданными параметрами
     * @param name имя клиента
     * @param passport паспортные данные клиента
     */
    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    public String getName() {
        return this.name;
    }

    public String getPassport() {
        return this.passport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name)
                && Objects.equals(passport, user.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, passport);
    }
}
