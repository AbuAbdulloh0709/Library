package com.epam.finaltask.library.controller.filter;

import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.RequestParameter;
import com.epam.finaltask.library.controller.command.SessionAttribute;
import com.epam.finaltask.library.controller.command.impl.CommandType;
import com.epam.finaltask.library.entity.enums.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import static com.epam.finaltask.library.controller.command.impl.CommandType.*;
import static com.epam.finaltask.library.entity.enums.UserRole.*;

/**
 * @author YanaV
 * The type Security access rights filter.
 * @project Production Center
 */
@WebFilter(urlPatterns = "/controller")
public class SecurityAccessRightsFilter implements Filter {
    private static final String DEFAULT_COMMAND = "go_to_home";
    private EnumMap<UserRole, List<CommandType>> accessibleCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        accessibleCommands = new EnumMap<>(UserRole.class);
        accessibleCommands.put(ADMIN, List.of(GO_TO_SIGN_UP, GO_TO_SIGN_IN, GO_TO_ADMIN, GO_TO_DASHBOARD, GO_TO_SHOW_BOOKS_FOR_GUEST,
                GO_TO_ADMINISTRATORS, GO_TO_LIBRARIANS, GO_TO_ALL_WAITING_STUDENTS, GO_TO_ALL_APPROVED_STUDENTS, GO_TO_ALL_STUDENTS,
                GO_TO_ALL_BOOKS, GO_TO_ADD_BOOK_GENRE, GO_TO_ADD_BOOKS, GO_TO_REQUESTED_BOOKS, SIGN_UP, SIGN_UP, SEARCH_ISSUED_BOOKS,
                ADD_ADMINISTRATOR, ADD_LIBRARIAN, CHANGE_USER_STATUS, GO_TO_ISSUED_BOOKS, GO_TO_BOOKS_TO_ISSUE, GO_TO_ISSUE_BOOK_BY_STUDENT,
                GO_TO_ISSUE_BOOK, ISSUE_BOOK, RETURN_ISSUED_BOOK, REJECT_REQUEST,ACCEPT_REQUEST,
                SEARCH_STUDENTS, CHANGE_USER_STATUS, ADD_BOOK_GENRE, EDIT_BOOK_GENRE, ADD_BOOK, ISSUE_BOOK_BY_STUDENT, DEFAULT));
        accessibleCommands.put(LIBRARIAN, List.of(GO_TO_SIGN_UP, GO_TO_SIGN_IN, GO_TO_ADMIN, GO_TO_DASHBOARD, GO_TO_SHOW_BOOKS_FOR_GUEST,
                GO_TO_ADMINISTRATORS, GO_TO_LIBRARIANS, GO_TO_ALL_WAITING_STUDENTS, GO_TO_ALL_APPROVED_STUDENTS, GO_TO_ALL_STUDENTS,
                GO_TO_ALL_BOOKS, GO_TO_ADD_BOOK_GENRE, GO_TO_ADD_BOOKS, GO_TO_REQUESTED_BOOKS, SIGN_UP, SIGN_UP, SEARCH_ISSUED_BOOKS,
                ADD_ADMINISTRATOR, ADD_LIBRARIAN, CHANGE_USER_STATUS, GO_TO_ISSUED_BOOKS, GO_TO_BOOKS_TO_ISSUE,
                GO_TO_ISSUE_BOOK, ISSUE_BOOK, RETURN_ISSUED_BOOK, REJECT_REQUEST, ACCEPT_REQUEST,
                SEARCH_STUDENTS, CHANGE_USER_STATUS, ADD_BOOK_GENRE, EDIT_BOOK_GENRE, ADD_BOOK, ISSUE_BOOK_BY_STUDENT, DEFAULT));
        accessibleCommands.put(STUDENT, List.of(GO_TO_SIGN_UP, GO_TO_SIGN_IN, GO_TO_SHOW_BOOKS_FOR_GUEST, GO_TO_DASHBOARD, GO_TO_BOOKS_TO_ISSUE,
                SIGN_UP, SIGN_UP, ISSUE_BOOK_BY_STUDENT,GO_TO_ISSUE_BOOK_BY_STUDENT, DEFAULT));
        accessibleCommands.put(GUEST, List.of(GO_TO_SIGN_UP, GO_TO_SIGN_IN, GO_TO_SHOW_BOOKS_FOR_GUEST, SIGN_UP, SIGN_IN,
                SIGN_UP, DEFAULT));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        UserRole role = UserRole.valueOf(String.valueOf(session.getAttribute(SessionAttribute.ROLE)).toUpperCase());
        String command = request.getParameter(RequestParameter.COMMAND);
        if (command == null) {
            command = DEFAULT_COMMAND;
        }
        CommandType commandType = CommandType.valueOf(command.toUpperCase());
        Optional<CommandType> foundCommandType = accessibleCommands.get(role)
                .stream()
                .filter(c -> c == commandType)
                .findFirst();
        if (foundCommandType.isEmpty()) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.ERROR_403);
            return;
        }
        chain.doFilter(request, response);
    }
}