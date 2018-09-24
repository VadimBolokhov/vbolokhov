package ru.job4j.crud;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Users service servlet.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UsersServlet extends HttpServlet {
    /** Input validation */
    private final ValidateService validator = ValidateService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        List<User> users = this.validator.findAll();
        req.setAttribute("users", users);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String action = req.getParameter("action");
        if (action.equals("delete")) {
            this.validator.delete(id);
        }
        resp.sendRedirect(req.getContextPath() + "/list");
    }
}
