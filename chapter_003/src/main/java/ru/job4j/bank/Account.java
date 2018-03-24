package ru.job4j.bank;

import java.util.Objects;

/**
 * Банковский счёт.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Account {
    /** Количество денег на счету */
    private double value;
    /** Номер счета */
    private String requisites;

    /**
     * Конструктор - создание счёта с заданными параметрами
     * @param value количество денег
     * @param requisites номер счета
     */
    public Account(double value, String requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    /**
     * Возвращает баланс
     * @return баланс
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Возвращает номер счёта
     * @return номер счёта
     */
    public String getRequisites() {
        return this.requisites;
    }

    /**
     * Перевод денег на заданный счёт
     * @param dest заданный счёт
     * @param amount размер перевода
     * @return true - если перевод произведен успешно, false - в противном случае
     */
    public boolean transfer(Account dest, double amount) {
        boolean success = false;
        if (dest != null && 0 < amount && amount < this.value) {
            this.value -= amount;
            dest.value += amount;
            success = true;
        }
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Double.compare(account.value, value) == 0
                && Objects.equals(requisites, account.requisites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, requisites);
    }

    @Override
    public String toString() {
        return String.format("%s, %a", this.requisites, this.value);
    }
}
