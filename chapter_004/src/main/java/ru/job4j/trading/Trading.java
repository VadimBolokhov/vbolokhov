package ru.job4j.trading;

import java.util.*;

/**
 * Система трейдинга.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Trading {
    /** Отображение биржевых стаканов */
    private Map<String, OrderBook> issuers;

    /**
     * Конструктор - создание объекта
     */
    public Trading() {
        this.issuers = new HashMap<>();
    }

    /**
     * Обработка запроса: добавление или удаление заявки
     * @param order заявка
     */
    public void process(Order order) {
        order.getOrderType().execute(order, this.issuers);
    }

    /**
     * Возвращает биржевой стакан для данного эмитента
     * @param book идентификатор ценной бумаги
     * @return стакан
     */
    public OrderBook getOrderBook(String book) {
        return this.issuers.get(book);
    }

    /**
     * Возвращает представление стакана для данного эмитента в виде таблицы
     * @param book эмитент
     * @return таблица
     */
    public List<TableRaw> getTable(String book) {
        return this.issuers.get(book).getTable();
    }
}

