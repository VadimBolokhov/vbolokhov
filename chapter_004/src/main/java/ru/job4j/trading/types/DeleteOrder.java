package ru.job4j.trading.types;

import ru.job4j.trading.Order;
import ru.job4j.trading.OrderBook;

import java.util.Map;

/**
 * Удаление заявки.
 */
public class DeleteOrder implements OrderType {
    @Override
    public void execute(Order order, Map<String, OrderBook> map) {
        OrderBook book = map.get(order.getBook());
        book.delete(order);
    }
}
