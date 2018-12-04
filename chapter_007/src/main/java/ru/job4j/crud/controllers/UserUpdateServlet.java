package ru.job4j.crud.controllers;

import ru.job4j.crud.models.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User editing servlet.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UserUpdateServlet extends HttpServlet {
    /** Input validation */
    private final Validate validator = ValidateService.getInstance();
    /** Location class instance containing countries and city list */
    private final Location location = Location.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = this.validator.findById(id).get();
        req.setAttribute("user", user);
        req.setAttribute("countries", this.location.getCountries());
        req.setAttribute("cities", this.location.getCities(user.getCountry()));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/update-form.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        User user = this.createUserWithId(req);
        this.validator.update(user);
        PrintWriter out = resp.getWriter();
        String url = "{\"url\": \"./list\"}";
        out.write(url);
        out.flush();
    }

    private User createUserWithId(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Role role = Role.valueOf(req.getParameter("role"));
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        return new User.Builder().id(id).name(name).email(email).password(password).role(role)
                .country(country).city(city)
                .build();
    }
}
