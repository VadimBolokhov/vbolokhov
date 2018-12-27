package ru.job4j.crud.controllers;

import ru.job4j.crud.models.User;
import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Sign in controller.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SignInServlet extends HttpServlet {
    private final Validate validateService = ValidateService.getInstance();

    @Override
    public void init() throws ServletException {
        this.validateService.initStore();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> data = this.findUser(login, password);
        resp.setContentType("application/json");
        if (data.isPresent()) {
            User user = data.get();
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            String url = "{\"url\": \"./list\"}";
            resp.getWriter().write(url);
        } else {
            String error = "{\"error\": \"Login is incorrect\"}";
            resp.getWriter().write(error);
        }
    }

    private Optional<User> findUser(String login, String password) {
        Optional<User> result = Optional.empty();
        List<User> users = this.validateService.findAll();
        for (User user : users) {
            if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
                result = Optional.of(user);
                break;
            }
        }
        return result;
    }
}
