package ru.job4j.tracker;

import java.util.*;
/**
 * Хранение заявок.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Tracker {
    /** Максимальное количество заявок */
    private static final int MAX_ITEMS = 100;
    /** ГСЧ для генерации идентификаторов заявок */
    private static final Random RN = new Random();
    /** Массив заявок */
    private final Item[] items = new Item[MAX_ITEMS];
    /** Индекс ячейки, где будет создана следующая заявка */
    private int position = 0;

    /**
     * Добавляет заявку в список и присваивает ей иднетификатор
     * @param item заявка
     * @return добавленная заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items[this.position++] = item;
        return item;
    }

    /**
     * Генерирует id заявки
     * @return идентификатор
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }

    /**
     * Заменяет заявку с заданным идентификатором
     * @param id идентификатор
     * @param replace замена
     */
    public void replace(String id, Item replace) {
        for (int i = 0; i < this.position; i++) {
            if (this.items[i] != null && this.items[i].getId().equals(id)) {
                replace.setId(this.generateId());
                this.items[i] = replace;
                break;
            }
        }
    }

    /**
     * Удаляет из списка заявку с заданным Id
     * @param id идентификатор заявки
     */
    public void delete(String id) {
        for (int i = 0; i < this.position; i++) {
            if (this.items[i] != null && this.items[i].getId().equals(id)) {
                System.arraycopy(this.items, i + 1, this.items, i, this.position - i - 1);
                this.items[--position] = null;
            }
        }
    }

    /**
     * Возвращает список всех заявок
     * @return все заявки
     */
    public Item[] getAll() {
        return Arrays.copyOf(this.items, this.position);
    }

    /**
     * Находит все заявки с заданным именем
     * @param key имя для поиска
     * @return список всех найденных заявок
     */
    public Item[] findByName(String key) {
        int counter = 0;
        Item[] result = new Item[MAX_ITEMS];
        for (Item item : this.items) {
            if (item != null && item.getName().equals(key)) {
                result[counter++] = item;
            }
        }
        return Arrays.copyOf(result, counter);
    }

    /**
     * Поиск заявки по идентификатору
     * @param id идентификатор
     * @return результат поиска
     */
    public Item findById(String id) {
        Item result = null;
        for (Item item : items) {
            if (item != null && item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return result;
    }
}
