package ru.job4j.trading;

import org.junit.Test;
import ru.job4j.trading.types.AddOrder;
import ru.job4j.trading.types.DeleteOrder;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Trading test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class TradingTest {
    @Test
    public void whenAddOrderThenOrderBookHasSameOrder() {
        Trading trade = new Trading();
        Order order = new Order(111, new AddOrder(), Order.Action.BID, "test", 1000d, 10);
        trade.process(order);
        List<TableRaw> result = trade.getTable("test");
        assertThat(trade.getOrderBook("test").size(), is(1));
        assertThat(result.get(0).getBid(), is(10));
        assertThat(result.get(0).getPrice(), is(1000d));
    }

    @Test
    public void whenDeleteOrderThenOrderBookHasNoSameOrder() {
        Trading trade = new Trading();
        Order addOrder = new Order(111, new AddOrder(), Order.Action.BID, "test", 1000d, 10);
        Order delOrder = new Order(111, new DeleteOrder(), Order.Action.BID, "test", 1000d, 0);
        trade.process(addOrder);
        trade.process(delOrder);
        List<TableRaw> result = trade.getTable("test");
        assertThat(trade.getOrderBook("test").size(), is(0));
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void whenAddBidOrderAndAskOrderWithHigherPriceThenOrdersMergedCorrectly() {
        Trading trade = new Trading();
        trade.process(new Order(111, new AddOrder(), Order.Action.BID, "test", 1000d, 100));
        trade.process(new Order(222, new AddOrder(), Order.Action.BID, "test", 1000d, 100));
        trade.process(new Order(333, new AddOrder(), Order.Action.ASK, "test", 1100d, 150));
        List<TableRaw> result = trade.getTable("test");
        assertThat(trade.getOrderBook("test").size(), is(1));
        assertThat(result.get(0).getAsk(), is(0));
        assertThat(result.get(0).getBid(), is(50));
        assertThat(result.get(0).getPrice(), is(1000d));
    }

    @Test
    public void whenAddAskOrderAndBidOrderWithLowerOrEqualPriceThenOrdersAreMerged() {
        Trading trade = new Trading();
        Order order = new Order(111, new AddOrder(), Order.Action.ASK, "test", 1100d, 100);
        trade.process(order);
        trade.process(new Order(222, new AddOrder(), Order.Action.BID, "test", 1000d, 150));
        trade.process(new Order(333, new AddOrder(), Order.Action.BID, "test", 900d, 100));
        List<TableRaw> result = trade.getTable("test");
        TableRaw expected = new TableRaw(50, 0, 1000d);
        assertThat(trade.getOrderBook("test").size(), is(2));
        assertThat(trade.getOrderBook("test").contains(order), is(false));
        assertThat(result.get(0), is(expected));
        assertThat(result.get(0).hashCode(), is(expected.hashCode()));
        assertThat(result.get(1), is(new TableRaw(100, 0, 900d)));
    }

    @Test
    public void whenAddOrdersOfOppositeTypesThenProcessCorrectly() {
        Trading trade = new Trading();
        trade.process(new Order(111, new AddOrder(), Order.Action.BID, "test", 1200d, 100));
        trade.process(new Order(222, new AddOrder(), Order.Action.BID, "test", 1100d, 100));
        trade.process(new Order(333, new AddOrder(), Order.Action.ASK, "test", 1100d, 150));
        List<TableRaw> result = trade.getTable("test");
        assertThat(result.get(0), is(new TableRaw(100, 0, 1200d)));
        assertThat(result.get(1), is(new TableRaw(0, 50, 1100d)));
    }

    @Test
    public void whenAddAskAndBidOrderWithSamePriceAndVolumeThenOrdersAnnihilate() {
        Trading trade = new Trading();
        trade.process(new Order(111, new AddOrder(), Order.Action.ASK, "test", 1000d, 100));
        trade.process(new Order(222, new AddOrder(), Order.Action.BID, "test", 1000d, 100));
        List<TableRaw> result = trade.getTable("test");
        assertThat(trade.getOrderBook("test").size(), is(0));
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void whenAddSeveralOrdersWithSamePriceAndTypeThenReturnsOneTableRaw() {
        Trading trade = new Trading();
        trade.process(new Order(111, new AddOrder(), Order.Action.ASK, "test", 1000d, 100));
        trade.process(new Order(222, new AddOrder(), Order.Action.ASK, "test", 1000d, 100));
        List<TableRaw> result = trade.getTable("test");
        assertThat(trade.getOrderBook("test").size(), is(2));
        assertThat(result.get(0), is(new TableRaw(0, 200, 1000d)));
    }
}