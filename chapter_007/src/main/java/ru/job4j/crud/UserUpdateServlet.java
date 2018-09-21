package ru.job4j.crud;

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
    private ValidateService validator = ValidateService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String id = req.getParameter("id");
        User user = this.validator.findById(id).get();
        String name = user.getName();
        String email = user.getEmail();
        String login = user.getLogin();
        PrintWriter out = resp.getWriter();
        out.append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\">\n")
                .append("<head>\n")
                .append("<meta charset=\"UTF-8\">\n")
                .append("<title>Edit user info</title>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append(String.format("<form action='%s/edit?id=%s' method='post'>\n", req.getContextPath(), id))
                .append("<table>\n")
                .append("<tr><td>Login:</td>\n")
                .append("<td>\n")
                .append(String.format("<input type='text' name='login' size='20' value='%s' disabled/>\n", login))
                .append("</td></tr>\n")
                .append("<tr><td>Name:</td>\n")
                .append("<td>\n")
                .append(String.format("<input type='text' name='name' size='20' value='%s'/>\n", name))
                .append("</td></tr>\n")
                .append("<tr><td>E-mail:</td>\n")
                .append("<td>\n")
                .append(String.format("<input type='text' name='email' size='20' value='%s'/>\n", email))
                .append("</td></tr>\n")
                .append("</table>\n")
                .append("<input type='submit' value='Submit'/>\n")
                .append("</form>\n")
                .append("</body>\n")
                .append("</html>");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = this.createUserWithId(req);
        resp.setContentType("text/html");
        String response = this.validator.update(user);
        PrintWriter out = resp.getWriter();
        out.append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\">\n")
                .append("<head>\n")
                .append("<meta charset=\"UTF-8\">\n")
                .append("<title>Update user</title>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append(response)
                .append(String.format("<form action='%s/list' method='get'>\n", req.getContextPath()))
                .append("<input type='submit' name='list' value='User list'/>")
                .append("</form>")
                .append("</body>\n")
                .append("</html>");
        out.flush();
    }

    private User createUserWithId(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        return new User(id, name, "", email);
    }
}
