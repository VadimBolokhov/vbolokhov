package ru.job4j.trading;

import java.util.*;

/**
 * Биржевой стакан.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class OrderBook {
    /** Множество заявок на продажу */
    private TreeSet<Order> bid;
    /** Множество заявок на покупку */
    private TreeSet<Order> ask;

    /**
     * Конструктор - создание объекта
     */
    public OrderBook() {
        this.bid = new TreeSet<>();
        this.ask = new TreeSet<>();
    }

    /**
     * Проверка, содержит ли данный стакан заданную заявку
     * @param order заявка
     * @return true - если заявка найдена
     */
    boolean contains(Order order) {
        return this.bid.contains(order) || this.ask.contains(order);
    }

    /**
     * Возвращает текущее количекство заявок в стакане
     * @return количество заявок
     */
    public int size() {
        return this.bid.size() + this.ask.size();
    }

    /**
     * Добавляет заявку в стакан
     * @param order добавляемая заявка
     * @return ссылка на стакан после добавления заявки
     */
    public OrderBook add(Order order) {
        if (order.getAction() == Order.Action.ASK) {
            this.addAskOrder(order);
        } else {
            this.addBidOrder(order);
        }
        return this;
    }

    /**
     * Добавляет заявку на покупку
     * @param order добавляемая заявка
     */
    private void addAskOrder(Order order) {
        boolean merged = false;
        if (!this.bid.isEmpty()) {
            Order current = this.bid.last();
            while (true) {
                if (current.getPrice() <= order.getPrice()) {
                    merged = this.merge(current, order);
                    if (merged || this.bid.isEmpty()) {
                        break;
                    }
                    current = this.bid.last();
                } else {
                    this.ask.add(order);
                    break;
                }
            }
        }
        if (!merged) {
            this.ask.add(order);
        }
    }

    /**
     * Добавляет заявку на продажу
     * @param order добавляемая заявка
     */
    private void addBidOrder(Order order) {
        boolean merged = false;
        if (!this.ask.isEmpty()) {
            Order current = this.ask.first();
            while (true) {
                if (current.getPrice() >= order.getPrice()) {
                    merged = this.merge(current, order);
                    if (merged || this.ask.isEmpty()) {
                        break;
                    }
                    current = this.ask.first();
                } else {
                    this.bid.add(order);
                    break;
                }
            }
        }
        if (!merged) {
            this.bid.add(order);
        }
    }

    /**
     * Обрабатывает заявки с противоположными действиями
     * @param current заявка из противоположного множества по отношению к типу {@code order}
     * @param order добавляемая заявка
     * @return результат обработки
     * (нужен для корректной работы методов {@code addAskOrder()} и {@code addBidOrder()})
     */
    private boolean merge(Order current, Order order) {
        boolean merged = false;
        int newVolume = current.getVolume() - order.getVolume();
        if (newVolume < 0) {
            order.setVolume(-newVolume);
            this.delete(current);
        } else if (newVolume > 0) {
            current.setVolume(newVolume);
            merged = true;
        } else {
            this.delete(current);
            merged = true;
        }
        return merged;
    }


    /**
     * Удаляет заявку из стакана
     * @param order удаляемая заявка
     */
    public void delete(Order order) {
        if (order.getAction() == Order.Action.ASK) {
            this.ask.remove(order);
        } else {
            this.bid.remove(order);
        }
    }

    /**
     * Создание строки таблицы
     * @param order заявка
     * @return строка таблицы
     */
    private TableRaw newRaw(Order order) {
        int[] cells = new int[2];
        cells[order.getAction().ordinal()] = order.getVolume();
        return new TableRaw(cells, order.getPrice());
    }

    /**
     * Возвращает предаставление стакана в виде таблицы
     * @return список строк
     */
    public List<TableRaw> getTable() {
        List<TableRaw> result = new ArrayList<>();
        TreeSet<Order> orders = new TreeSet<>(this.bid);
        orders.addAll(this.ask);
        Order current = orders.pollFirst();
        if (current != null) {
            Order next = orders.pollFirst();
            while (true) {
                if (next != null) {
                    if (current.getPrice() == next.getPrice()) {
                        current.setVolume(current.getVolume() + next.getVolume());
                    } else {
                        result.add(newRaw(current));
                        current = next;
                    }
                } else {
                    result.add(newRaw(current));
                    break;
                }
                next = orders.pollFirst();
            }
        }
        return result;
    }
}
