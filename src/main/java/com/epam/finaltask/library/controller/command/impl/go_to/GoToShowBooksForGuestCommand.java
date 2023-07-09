package com.epam.finaltask.library.controller.command.impl.go_to;

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

import java.util.List;

public class GoToShowBooksForGuestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_PAGE = 1;
    private final BookService bookService = BookServiceImpl.getInstance();
    private final GenreService genreService = GenreServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        int page;
        int genre_id = 0;
        if (request.getParameter(RequestParameter.BOOK_GENRE_ID) != null) {
            genre_id = Integer.parseInt(request.getParameter(RequestParameter.BOOK_GENRE_ID));
        }
        if (request.getParameter(RequestParameter.PAGE) == null) {
            page = DEFAULT_PAGE;
        } else {
            page = Integer.parseInt(request.getParameter(RequestParameter.PAGE));
        }

        try {
            List<Genre> genres = genreService.getAllGenres();
            List<Book> books = genre_id == 0 ? bookService.getAllBooks(page) : bookService.getAllBooksByGenre(genre_id, page);
            List<Book> nextBooks = genre_id == 0 ? bookService.getAllBooks(page + 1) : bookService.getAllBooksByGenre(genre_id, page + 1);
            request.setAttribute(RequestAttribute.GENRES, genres);
            request.setAttribute(RequestAttribute.BOOKS, books);
            if (genre_id != 0) request.setAttribute(RequestAttribute.BOOK_GENRE_ID, genre_id);
            request.setAttribute(RequestAttribute.PAGE, page);
            request.setAttribute(RequestAttribute.LAST, nextBooks.isEmpty());
            return new Router(PagePath.SHOW_BOOKS_FOR_GUEST, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to browse books page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
