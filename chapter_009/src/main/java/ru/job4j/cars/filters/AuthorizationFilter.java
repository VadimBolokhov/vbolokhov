package ru.job4j.cars.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Authorization filter.
 * @author Vadim Bolokhov
 */
public class AuthorizationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) {
        this.context = fConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.setAttribute("error", "You need to be logged in.");
            servletRequest.getRequestDispatcher("/WEB-INF/views/cars/errorpage.jsp")
                    .forward(servletRequest, servletResponse);
        }
    }
}
