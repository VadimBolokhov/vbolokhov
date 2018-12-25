package ru.job4j.cinema.controllers;

import ru.job4j.cinema.service.Service;
import ru.job4j.cinema.service.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Validate service stub for Service layer
 * @author Vadim Bolokhov
 */
public class ServiceStub implements Service {
    private List<Ticket> seats = new ArrayList<>();

    @Override
    public List<Ticket> getAllSeats() {
        return this.seats;
    }

    @Override
    public Optional<Ticket> findSeatById(int seat) {
        return this.seats.stream()
                .filter(ticket -> ticket.getId() == seat)
                .findFirst();
    }

    @Override
    public boolean reserve(Ticket ticket) {
        this.findSeatById(ticket.getId()).get().setReserved(true);
        return true;
    }

    @Override
    public void initHall(int rows, int seats) {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= rows; j++) {
                int key = 100 * i + j;
                this.seats.add(new Ticket(key));
            }
        }
    }
}
