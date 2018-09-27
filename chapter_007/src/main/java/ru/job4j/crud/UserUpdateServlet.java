package ru.job4j.crud;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User editing servlet.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UserUpdateServlet extends HttpServlet {
    /** Input validation */
    private final ValidateService validator = ValidateService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = this.validator.findById(id).get();
        req.setAttribute("user", user);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/update-form.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = this.createUserWithId(req);
        String message = this.validator.update(user);
        req.setAttribute("message", message);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/update-user.jsp");
        dispatcher.forward(req, resp);
    }

    private User createUserWithId(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        return new User(id, name, "", email);
    }
}
