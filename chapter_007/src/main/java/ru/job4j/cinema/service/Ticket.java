package ru.job4j.cinema.service;

/**
 * Ticket information
 * @author Vadim Bolokhov
 */
public class Ticket {
    /** Seat id */
    private int id;
    /** True if the seat is reserved, otherwise false */
    private boolean reserved;
    /** Ticket price */
    private double price;
    /** Owner's username */
    private String username;
    /** Owner's phone */
    private String phone;

    public Ticket() { }

    public Ticket(int id) {
        this(id, false);
    }

    public Ticket(int id, boolean reserved) {
        this.id = id;
        this.reserved = reserved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
