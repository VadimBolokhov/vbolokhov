package ru.job4j.crud;

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
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        List<User> users = this.validator.findAll();
        StringBuilder table = new StringBuilder("<table>\n");
        table.append("<tr><th>ID</th><th>Login</th><th>Name</th><th>E-mail</th><th>Creation date</th></tr>\n");
        for (User user : users) {
            String id = user.getId();
            table.append("<tr>\n<td>\n")
                    .append(user.getId())
                    .append("</td>\n<td>\n")
                    .append(user.getLogin())
                    .append("</td>\n<td>\n")
                    .append(user.getName())
                    .append("</td>\n<td>\n")
                    .append(user.getEmail())
                    .append("</td>\n<td>\n")
                    .append(user.getCreateDate())
                    .append("</td>\n<td>\n")
                    .append(String.format("<form action='%s/edit' method='get'>\n", req.getContextPath()))
                    .append(String.format("<input type='hidden' name='id' value='%s'/>\n", id))
                    .append("<input type='submit' value='Edit'/>\n")
                    .append("</form>\n")
                    .append("</td><td>\n")
                    .append(String.format("<form action='%s/list' method='post'>\n", req.getContextPath()))
                    .append(String.format("<input type='hidden' name='id' value='%s'/>\n", id))
                    .append("<input type='hidden' name='action' value='delete'/>\n")
                    .append("<input type='submit' value='X'/>\n")
                    .append("</form>\n</td>\n</tr>\n");
        }
        table.append("<tr><td>\n")
                .append(String.format("<form action='%s/create' method='get'>\n", req.getContextPath()))
                .append("<input type='submit' value='+'/>")
                .append("</form>\n</td>\n</tr>\n</table>\n");
        out.append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\">\n")
                .append("<head>\n")
                .append("<meta charset=\"UTF-8\">\n")
                .append("<title>User list</title>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append(table)
                .append("</body>\n")
                .append("</html>");
        out.flush();
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
