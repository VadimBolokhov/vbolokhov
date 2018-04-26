package ru.job4j.trading.types;

import ru.job4j.trading.Order;
import ru.job4j.trading.OrderBook;

import java.util.Map;

/**
 * Добавление заявки.
 */
public class AddOrder implements OrderType {
    @Override
    public void execute(Order order, Map<String, OrderBook> issuers) {
        String issuer = order.getBook();
        if (issuers.containsKey(issuer)) {
            issuers.get(issuer).add(order);
        } else {
            issuers.put(issuer, new OrderBook().add(order));
        }
    }
}
