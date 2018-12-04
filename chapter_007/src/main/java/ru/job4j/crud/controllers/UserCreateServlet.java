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
 * User creation servlet.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UserCreateServlet extends HttpServlet {
    /** Input validation */
    private final Validate validator = ValidateService.getInstance();
    /** Location class instance containing countries and city list */
    private final Location location = Location.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("countries", this.location.getCountries());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/create-form.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        User user = this.createUserWithLogin(req);
        String jsonString = this.validator.add(user)
                ? "{\"url\": \"./list\"}" : "{\"error\": \"User already exists\"}";
        PrintWriter out = resp.getWriter();
        out.write(jsonString);
        out.flush();
    }

    private User createUserWithLogin(HttpServletRequest req) {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        Role role = Role.valueOf(req.getParameter("role"));
        return new User.Builder().name(name).login(login).password(password).email(email).role(role)
                .country(country).city(city)
                .build();
    }
}
