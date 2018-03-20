package ru.job4j.tracker;

import java.util.*;
/**
 * Хранение заявок.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Tracker {
    /** ГСЧ для генерации идентификаторов заявок */
    private static final Random RN = new Random();
    /** Массив заявок */
    private final List<Item> items = new ArrayList<>();

    /**
     * Добавляет заявку в список и присваивает ей иднетификатор
     * @param item заявка
     * @return добавленная заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
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
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).getId().equals(id)) {
                replace.setId(id);
                this.items.set(i, replace);
                break;
            }
        }
    }

    /**
     * Удаляет из списка заявку с заданным Id
     * @param id идентификатор заявки
     */
    public void delete(String id) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).getId().equals(id)) {
                this.items.remove(i);
            }
        }
    }

    /**
     * Возвращает список всех заявок
     * @return все заявки
     */
    public List<Item> getAll() {
        return this.items;
    }

    /**
     * Находит все заявки с заданным именем
     * @param key имя для поиска
     * @return список всех найденных заявок
     */
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getName().equals(key)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Поиск заявки по идентификатору
     * @param id идентификатор
     * @return результат поиска
     */
    public Optional<Item> findById(String id) {
        Item result = null;
        for (Item item : items) {
            if (item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return Optional.ofNullable(result);
    }
}
