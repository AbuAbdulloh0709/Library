package com.epam.finaltask.library.controller.command.impl.go_to;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.RequestParameter;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.BookService;
import com.epam.finaltask.library.model.service.UserService;
import com.epam.finaltask.library.model.service.impl.BookServiceImpl;
import com.epam.finaltask.library.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class GoToIssueBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService = BookServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        int book_id;

        String searchText = request.getParameter(RequestParameter.SEARCH_TEXT);
        if (searchText != null && searchText.trim().isEmpty()) {
            searchText = null;
        }

        if (request.getParameter(RequestParameter.BOOK_ID) == null) {
            return new Router(PagePath.ERROR_404, Router.RouterType.FORWARD);
        }

        book_id = Integer.parseInt(request.getParameter(RequestParameter.BOOK_ID));
        try {
            Optional<Book> book = bookService.findById(book_id);
            if (book.isPresent()) {

                if (searchText != null) {
                    Optional<User> student = userService.findUserByPassportOrEmail(searchText);
                    student.ifPresent(user -> request.setAttribute(RequestParameter.STUDENT, user));
                }

                request.setAttribute(RequestParameter.BOOK, book.get());
                return new Router(PagePath.ISSUE_BOOK, Router.RouterType.FORWARD);
            } else {
                LOGGER.error("Book is not available given by id");
                return new Router(PagePath.ERROR_404, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to issue book page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }

    }
}
