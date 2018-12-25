package ru.job4j.crud.filters;

import ru.job4j.crud.models.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Authorization servlet filter.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class AuthorizationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) {
        this.context = fConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/signin")
                || requestURI.contains("/signout")) {
            filterChain.doFilter(req, resp);
        } else {
            HttpSession session = request.getSession();
            if (session.getAttribute("user") != null) {
                filterChain.doFilter(req, resp);
            }
            ((HttpServletResponse) resp).sendRedirect(String.format(
                    "%s/signin",
                    ((HttpServletRequest) req).getContextPath()
            ));
        }
    }
}
