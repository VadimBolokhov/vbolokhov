package ru.job4j.crud;

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
    private ValidateService validator = ValidateService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\">\n")
                .append("<head>\n")
                .append("<meta charset=\"UTF-8\">\n")
                .append("<title>User creation</title>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append(String.format("<form action='%s/create' method='post'>\n", req.getContextPath()))
                .append("<table>\n")
                .append("<tr><td>Login:</td>\n")
                .append("<td>\n")
                .append("<input type='text' name='login' size='20'/>\n")
                .append("</td></tr>\n")
                .append("<tr><td>Name:</td>\n")
                .append("<td>\n")
                .append("<input type='text' name='name' size='20'/>\n")
                .append("</td></tr>\n")
                .append("<tr><td>E-mail:</td>\n")
                .append("<td>\n")
                .append("<input type='text' name='email' size='20'/>\n")
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
        User user = this.createUserWithLogin(req);
        resp.setContentType("text/html");
        String response = this.validator.add(user);
        PrintWriter out = resp.getWriter();
        out.append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\">\n")
                .append("<head>\n")
                .append("<meta charset=\"UTF-8\">\n")
                .append("<title>User creation</title>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append(response)
                .append(String.format("<form action='%s/list' method='get'>\n", req.getContextPath()))
                .append("<input type='submit' name='list' value='User list'/>\n")
                .append("</form>\n")
                .append(String.format("<form action='%s/create' method='get'>\n", req.getContextPath()))
                .append("<input type='submit' name='new' value='New user'/>\n")
                .append("</form>\n")
                .append("</body>\n")
                .append("</html>");
        out.flush();
    }

    private User createUserWithLogin(HttpServletRequest req) {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        return new User("", name, login, email);
    }
}
