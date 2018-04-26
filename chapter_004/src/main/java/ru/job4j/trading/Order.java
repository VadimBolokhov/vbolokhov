package ru.job4j.trading;

import ru.job4j.trading.types.OrderType;

/**
 * Заявка.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Order implements Comparable<Order> {
    /** Уникальный ключ заявки */
    private int id;
    /** Тип запроса: выставить заявку на торги или снять */
    private OrderType type;
    /** Операция: купить или продать */
    private Action action;
    /** Идентификатор ценной бумаги */
    private String book;
    /** Цена */
    private double price;
    /** Количество акций для покупки или продажи */
    private int volume;

    /**
     * Перечисление типов заявок
     */
    enum Action { BID, ASK }

    public Order(int id, OrderType type, Action action, String book, double price, int volume) {
        this.id = id;
        this.type = type;
        this.action = action;
        this.book = book;
        this.price = price;
        this.volume = volume;
    }

    public int getId() {
        return this.id;
    }

    public OrderType getOrderType() {
        return this.type;
    }

    public Action getAction() {
        return this.action;
    }

    public String getBook() {
        return this.book;
    }

    public double getPrice() {
        return this.price;
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public int compareTo(Order o) {
        int result = Double.compare(o.price, this.price);
        return result != 0 ? result : this.id - o.id;
    }
}
