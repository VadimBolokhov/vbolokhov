package ru.job4j.cinema.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.service.CinemaService;
import ru.job4j.cinema.service.JsonRequestConverter;
import ru.job4j.cinema.service.Service;
import ru.job4j.cinema.service.Ticket;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Seat booking servlet.
 * @author Vadim Bolokhov
 */
public class HallServlet extends HttpServlet {
    /** Logger */
    private static final Logger LOG = LogManager.getLogger(HallServlet.class.getName());
    /** Validation service */
    private final Service service = CinemaService.getInstance();

    @Override
    public void init() {
        this.service.initHall(3, 5);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String query = req.getParameter("action");
        if (query != null) {
            if (query.equals("getPrice")) {
                this.generatePriceResponse(req, resp);
            } else if (query.equals("getSeatList")) {
                this.generateSeatListResponse(resp);
            }
        }
    }

    private void generatePriceResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int seat = Integer.parseInt(req.getParameter("seat"));
        Optional<Ticket> data = this.service.findSeatById(seat);
        if (data.isPresent()) {
            Ticket priceResponse = data.get();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getOutputStream(), priceResponse);
        } else {
            this.generateSuccessResponse(false, resp);
        }

    }

    private void generateSeatListResponse(HttpServletResponse resp) throws IOException {
        List<Ticket> seatList = this.service.getAllSeats();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(), seatList);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        Ticket ticket = this.getSeatInfoFromRequest(req);
        boolean success = this.service.reserve(ticket);
        this.generateSuccessResponse(success, resp);
    }

    private Ticket getSeatInfoFromRequest(HttpServletRequest req) throws IOException {
        String json = new JsonRequestConverter().convertRequestToString(req);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Ticket.class);
    }

    private void generateSuccessResponse(boolean success, HttpServletResponse resp) throws IOException {
        StringJoiner jsonString = new StringJoiner(", ", "{", "}");
        jsonString.add("\"url\" : \"index.html\"");
        if (!success) {
            jsonString.add("\"error\" : \"Sorry, the seat is unavailable, please select another one\"");
        }
        PrintWriter out = resp.getWriter();
        out.write(jsonString.toString());
        out.flush();
    }
}