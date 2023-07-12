package com.epam.finaltask.library.controller.command.impl.orders;

import com.epam.finaltask.library.controller.command.*;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.BookService;
import com.epam.finaltask.library.model.service.GenreService;
import com.epam.finaltask.library.model.service.OrderService;
import com.epam.finaltask.library.model.service.impl.BookServiceImpl;
import com.epam.finaltask.library.model.service.impl.GenreServiceImpl;
import com.epam.finaltask.library.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

import static com.epam.finaltask.library.controller.command.RequestParameter.*;

public class IssueBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final BookService bookService = BookServiceImpl.getInstance();
    private final GenreService genreService = GenreServiceImpl.getInstance();
    private static final String ORDER_DETAIL_STATUS = "created";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        HashMap<String, String> orderMap = new HashMap<>();

        String order_detail_user_id = String.valueOf(session.getAttribute(SessionAttribute.USER_ID));
        orderMap.put(ORDER_DETAIL_USER_ID, order_detail_user_id);

        orderMap.put(ORDER_USER_ID, request.getParameter(STUDENT_ID));
        orderMap.put(BOOK_ID, request.getParameter(BOOK_ID));
        orderMap.put(RequestParameter.ORDER_TYPE, request.getParameter(RequestParameter.ORDER_TYPE));
        orderMap.put(RequestParameter.ORDER_DETAIL_STATUS, ORDER_DETAIL_STATUS);
        orderMap.put(ISSUE_DATE, request.getParameter(ISSUE_DATE));
        orderMap.put(RETURN_DATE, request.getParameter(RETURN_DATE));

        try {
            orderService.createOrder(orderMap);
            int page = 1;
            List<Genre> genres = genreService.getAllGenres();
            List<Book> books = bookService.getAllBooks(page);
            List<Book> nextBooks = bookService.getAllBooks(page + 1);
            request.setAttribute(RequestAttribute.GENRES, genres);
            request.setAttribute(RequestAttribute.BOOKS, books);
            request.setAttribute(RequestAttribute.PAGE, page);
            request.setAttribute(RequestAttribute.LAST, nextBooks.isEmpty());
            return new Router(PagePath.BOOKS_TO_ISSUE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to books to issue page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
