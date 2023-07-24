package com.epam.finaltask.library.controller.command.impl.go_to;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.RequestParameter;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.BookService;
import com.epam.finaltask.library.model.service.GenreService;
import com.epam.finaltask.library.model.service.OrderService;
import com.epam.finaltask.library.model.service.UserService;
import com.epam.finaltask.library.model.service.impl.BookServiceImpl;
import com.epam.finaltask.library.model.service.impl.GenreServiceImpl;
import com.epam.finaltask.library.model.service.impl.OrderServiceImpl;
import com.epam.finaltask.library.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoToDashboardCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService = BookServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            int book_counts = bookService.bookCounts();
            int student_counts = userService.countStudents();
            int librarian_counts = userService.countLibrarians();
            request.setAttribute(RequestParameter.BOOK_COUNT,book_counts);
            request.setAttribute(RequestParameter.STUDENT_COUNT,student_counts);
            request.setAttribute(RequestParameter.LIBRARIAN_COUNT,librarian_counts);
            return new Router(PagePath.DASHBOARD, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to dashboard page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
