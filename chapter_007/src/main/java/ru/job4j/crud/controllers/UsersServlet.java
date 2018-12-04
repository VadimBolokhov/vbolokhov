package ru.job4j.crud.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.crud.models.Location;
import ru.job4j.crud.models.User;
import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

/**
 * Users service servlet.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UsersServlet extends HttpServlet {
    /** Input validation */
    private final Validate validator = ValidateService.getInstance();
    /** Location class instance containing countries and city list */
    private final Location location = Location.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equals("getCities")) {
            String country = req.getParameter("country");
            List<String> cities = this.location.getCities(country);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getOutputStream(), cities);
        } else {
            List<User> users = this.validator.findAll();
            req.setAttribute("users", users);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/user-list.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String action = req.getParameter("action");
        if (action.equals("delete")) {
            this.validator.delete(id);
        }
        doGet(req, resp);
    }
}
