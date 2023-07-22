package com.epam.finaltask.library.controller.command.impl.book;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.RequestParameter;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.BookService;
import com.epam.finaltask.library.model.service.GenreService;
import com.epam.finaltask.library.model.service.impl.BookServiceImpl;
import com.epam.finaltask.library.model.service.impl.GenreServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditBookInfoBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService = BookServiceImpl.getInstance();
    private final GenreService genreService = GenreServiceImpl.getInstance();
    private static final int DEFAULT_PAGE = 1;
    private static final String EDIT_BOOK_SUCCESS_MESSAGE = "book.edited";
    private static final String BOOK_AVAILABLE_TITLE_MESSAGE = "book.title.occupied";

    @Override
    public Router execute(HttpServletRequest request) {
        String title = request.getParameter(RequestParameter.BOOK_TITLE);
        Map<String, String> bookMap = new HashMap<>();
        bookMap.put(RequestParameter.BOOK_TITLE, title);
        bookMap.put(RequestParameter.BOOK_ID, request.getParameter(RequestParameter.BOOK_ID));
        bookMap.put(RequestParameter.BOOK_DESCRIPTION, request.getParameter(RequestParameter.BOOK_DESCRIPTION));
        bookMap.put(RequestParameter.BOOK_COPIES, request.getParameter(RequestParameter.BOOK_COPIES));
        bookMap.put(RequestParameter.BOOK_AUTHOR, request.getParameter(RequestParameter.BOOK_AUTHOR));
        bookMap.put(RequestParameter.BOOK_GENRE_ID, request.getParameter(RequestParameter.BOOK_GENRE_ID));
        int book_id = Integer.parseInt(request.getParameter(RequestParameter.BOOK_ID));
        try {
            if (bookService.isBookTitleOccupied(title,book_id)) {
                List<Genre> genres = genreService.getAllGenres();
                Book book = bookService.findById(book_id).get();
                request.setAttribute(RequestAttribute.BOOK, book);
                request.setAttribute(RequestAttribute.GENRES, genres);
                request.setAttribute(RequestAttribute.MESSAGE, BOOK_AVAILABLE_TITLE_MESSAGE);
                return new Router(PagePath.EDIT_BOOK_INFO, Router.RouterType.FORWARD);
            }
            bookService.updateBook(bookMap);

            List<Genre> genres = genreService.getAllGenres();
            List<Book> books = bookService.getAllBooks(DEFAULT_PAGE);
            List<Book> nextBooks = bookService.getAllBooks(DEFAULT_PAGE + 1);
            request.setAttribute(RequestAttribute.GENRES, genres);
            request.setAttribute(RequestAttribute.BOOKS, books);
            request.setAttribute(RequestAttribute.PAGE, DEFAULT_PAGE);
            request.setAttribute(RequestAttribute.LAST, nextBooks.isEmpty());
            request.setAttribute(RequestAttribute.MESSAGE, EDIT_BOOK_SUCCESS_MESSAGE);
            return new Router(PagePath.ALL_BOOKS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to add book page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }

}
