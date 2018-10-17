package ru.job4j.crud.controllers;

import ru.job4j.crud.models.Role;
import ru.job4j.crud.models.User;
import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User creation servlet.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UserCreateServlet extends HttpServlet {
    /** Input validation */
    private final Validate validator = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/create-form.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = this.createUserWithLogin(req);
        String message = this.validator.add(user);
        req.setAttribute("message", message);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/create-user.jsp");
        dispatcher.forward(req, resp);
    }

    private User createUserWithLogin(HttpServletRequest req) {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        Role role = Role.valueOf(req.getParameter("role"));
        return new User.Builder().name(name).login(login).password(password).email(email).role(role)
                .build();
    }
}
