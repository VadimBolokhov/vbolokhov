package ru.job4j.cinema.persistance;

import ru.job4j.cinema.service.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Persistense layer interface.
 * @author Vadim Bolokhov
 */
public interface Persistence {
    /**
     * Add seat to empty hall
     * @param rows number of rows
     * @param seats number of seats per row
     */
    void addSeats(int rows, int seats);

    /**
     * Returns list of all seats
     * @return list of all seats
     */
    List<Ticket> getAllSeats();

    /**
     * Reserve a seat
     * @param ticket seat information
     * @return {@code true} if the seat is not occupied and operation succeeds,
     * otherwise - {@code false}
     */
    boolean reserveSeat(Ticket ticket);

    /**
     * Return seat info
     * @param seat seat id
     * @return ticket
     */
    Optional<Ticket> getSeatInfo(int seat);

    /**
     * Create empty tables in the DB
     */
    void initDB();
}
