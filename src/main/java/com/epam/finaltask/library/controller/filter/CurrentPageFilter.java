package com.epam.finaltask.library.controller.filter;

import com.epam.finaltask.library.controller.command.RequestParameter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.epam.finaltask.library.controller.command.SessionAttribute.CURRENT_PAGE;

/**
 * @author YanaV
 * The type Current page filter.
 * @project Production Center
 */
@WebFilter(urlPatterns = {"/controller", "/pages/*"})
public class CurrentPageFilter implements Filter {
    private static final String COMMAND_DELIMITER = "?";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        if (httpServletRequest.getParameter(RequestParameter.COMMAND) != null) {
            String currentPage = httpServletRequest.getServletPath() + COMMAND_DELIMITER + httpServletRequest.getQueryString();
            session.setAttribute(CURRENT_PAGE, currentPage);
        } else {
            session.setAttribute(CURRENT_PAGE, httpServletRequest.getServletPath());
        }
        chain.doFilter(request, response);
    }
}
