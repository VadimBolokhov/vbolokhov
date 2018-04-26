package ru.job4j.trading;

import java.util.Arrays;
import java.util.Objects;

/**
 * Строка таблицы.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class TableRaw {
    /** Массив количества заявок: [0] - на продажу, [1] - покупку */
    private int[] bidAsk;
    /** Цена */
    private double price;

    TableRaw(int[] cells, double price) {
        this.bidAsk = cells;
        this.price = price;
    }

    TableRaw(int bid, int ask, double price) {
        this.bidAsk = new int[] {bid, ask};
        this.price = price;
    }

    public int getBid() {
        return this.bidAsk[0];
    }

    public int getAsk() {
        return this.bidAsk[1];
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TableRaw raw = (TableRaw) o;
        return Double.compare(raw.price, price) == 0
                && Arrays.equals(bidAsk, raw.bidAsk);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(price);
        result = 31 * result + Arrays.hashCode(bidAsk);
        return result;
    }
}
