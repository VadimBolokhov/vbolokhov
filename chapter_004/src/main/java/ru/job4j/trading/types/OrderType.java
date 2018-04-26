package ru.job4j.trading.types;

import ru.job4j.trading.Order;
import ru.job4j.trading.OrderBook;

import java.util.Map;

/**
 * Интерфейс типа запроса
 */
public interface OrderType {
    /**
     * Обрабатывает заявку
     * @param order обрабатываемая зхаявка
     * @param map хранилище биржевых стаканов
     */
    void execute(Order order, Map<String, OrderBook> map);
}
