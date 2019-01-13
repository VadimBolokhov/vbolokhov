package ru.job4j.cinema.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cinema booking servlet filter.
 * Filters GET request to prevent direct access to the payment page without
 * inclusion of required info (such as seat ID).
 * @author Vadim Bolokhov
 */
public class BookingFilter implements Filter {
    /** Filter config */
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String method = req.getMethod();
        if (method.equalsIgnoreCase("GET")) {
            String seat = req.getParameter("seat");
            String action = req.getParameter("action");
            if (seat == null && action == null) {
                HttpServletResponse resp = (HttpServletResponse) servletResponse;
                resp.sendRedirect(String.format("%s/cinema/index.html", req.getContextPath()));
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
