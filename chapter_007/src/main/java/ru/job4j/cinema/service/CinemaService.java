package ru.job4j.cinema.service;

import ru.job4j.cinema.persistance.CinemaDBProcessor;
import ru.job4j.cinema.persistance.Persistence;

import java.util.List;
import java.util.Optional;

/**
 * Validate service for Service layer.
 * @author Vadim Bolokhov
 */
public enum CinemaService implements Service {
    INSTANCE;
    /** Storage */
    private final Persistence store = CinemaDBProcessor.INSTANCE;
    /** Sync object */
    private final Object lock = new Object();

    public static Service getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Ticket> getAllSeats() {
        return this.store.getAllSeats();
    }

    @Override
    public Optional<Ticket> findSeatById(int seat) {
        return this.store.getSeatInfo(seat);
    }

    @Override
    public boolean reserve(Ticket ticket) {
        boolean success = false;
        synchronized (this.lock) {
            Ticket seatInfo = this.store.getSeatInfo(ticket.getId()).get();
            if (!seatInfo.isReserved()) {
                success = this.store.reserveSeat(ticket);
            }
        }
        return success;
    }

    @Override
    public void initHall(int rows, int seats) {
        this.store.initDB();
        this.store.addSeats(rows, seats);
    }
}
