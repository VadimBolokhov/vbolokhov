package ru.job4j.crud.controllers;

import ru.job4j.crud.models.User;
import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * User service servlet.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UserServlet extends HttpServlet {
    /** Input validation */
    private final Validate validateService = ValidateService.getInstance();
    /** Action dispatcher */
    private final Map<String, Function<User, String>> dispatch = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String id = req.getParameter("id");
        if (id != null) {
            this.returnUserIfExists(out, id);
        } else {
            this.returnAllUsers(out);
        }
        out.flush();
    }

    private void returnUserIfExists(PrintWriter out, String id) {
        Optional<User> user = this.validateService.findById(id);
        String response = user.isPresent()
                ? user.get().toString()
                : String.format("User with id = %s does not exist", id);
        out.println(response);
    }

    private void returnAllUsers(PrintWriter out) {
        List<User> users = this.validateService.findAll();
        if (users.isEmpty()) {
            out.println("No users found.");
        } else {
            users.forEach(out::println);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = createUser(req);
        String action = req.getParameter("action");
        String response = this.dispatchAction(action, user);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println(response);
    }

    private User createUser(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        return new User.Builder().id(id).login(login).password(password).name(name)
                .email(email).build();
    }

    private String dispatchAction(String action, User user) {
        return this.dispatch.get(action).apply(user);
    }

    @Override
    public void init() throws ServletException {
        this.dispatch.put("add", this.validateService::add);
        this.dispatch.put("update", this.validateService::update);
        this.dispatch.put("delete", user -> this.validateService.delete(user.getId()));
    }
}
