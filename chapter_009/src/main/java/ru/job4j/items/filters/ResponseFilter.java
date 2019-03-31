package ru.job4j.items.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Items servlet filter.
 * @author Vadim Bolokhov
 */
public class ResponseFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("application/json");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
