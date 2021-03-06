package ru.job4j.nonblocking;

import java.util.Objects;

/**
 * Модель данных.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Base {
    /** Идентификатор модели */
    private int id;
    /** Версия */
    private int version;

    /**
     * Конструктор - создание объекта с заданным параметром
     * @param id идентификатор
     */
    public Base(int id) {
        this.id = id;
    }

    /**
     * Возвращает идентификатор модели
     * @return идентификатор модели
     */
    public int getId() {
        return this.id;
    }

    /**
     * Возвращает версию модели
     * @return версия модели
     */
    public int getVersion() {
        return this.version;
    }

    /**
     * Увеличивает значение версии на единицу
     */
    public void updateVersion() {
        this.version++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Base base = (Base) o;
        return id == base.id
                && version == base.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
