package ru.job4j.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cinema.service.JsonRequestConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JSON servlet.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class JsonServlet extends HttpServlet {
    /** Persons store */
    private final Map<Integer, Person> persons = new ConcurrentHashMap<>();
    /** Next id to be generated */
    private int key = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(), this.persons);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String data = new JsonRequestConverter().convertRequestToString(req);
        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(data, Person.class);
        this.persons.put(this.key++, person);
    }
}
