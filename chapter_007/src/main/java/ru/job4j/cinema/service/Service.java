package ru.job4j.cinema.service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer.
 * @author Vadim Bolokhov
 */
public interface Service {

    /**
     * Returns list of all seats
     * @return list of all seats
     */
    List<Ticket> getAllSeats();

    /**
     * Returns price of the seat with specified id
     * @param seat seat id
     * @return price
     */
    Optional<Ticket> findSeatById(int seat);

    /**
     * Reserve a seat
     * @param ticket seat information
     * @return {@code true} if the seat is not occupied and operation succeeds,
     * otherwise - {@code false}
     */
    boolean reserve(Ticket ticket);

    /**
     * Add seats to empty hall
     * @param rows number of rows
     * @param seats number of seats in a row
     */
    void initHall(int rows, int seats);
}
