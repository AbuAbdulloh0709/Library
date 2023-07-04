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

import java.util.List;

public class AddBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService = BookServiceImpl.getInstance();
    private final GenreService genreService = GenreServiceImpl.getInstance();
    private static final String ADD_BOOK_SUCCESS_MESSAGE = "book.added";
    private static final String ADD_BOOK_AVAILABLE_TITLE_MESSAGE = "book.title.occupied";

    @Override
    public Router execute(HttpServletRequest request) {
        String title = request.getParameter(RequestParameter.BOOK_TITLE);
        String description = request.getParameter(RequestParameter.BOOK_DESCRIPTION);
        String author = request.getParameter(RequestParameter.BOOK_AUTHOR);
        int genre_id = Integer.parseInt(request.getParameter(RequestParameter.BOOK_GENRE_ID));
        int book_copies = Integer.parseInt(request.getParameter(RequestParameter.BOOK_COPIES));
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setAuthor(author);
        book.setBookCopies(book_copies);
        book.setGenreId(genre_id);

        try {
            if (bookService.isBookTitleOccupied(title)) {
                List<Genre> genres = genreService.getAllGenres();
                request.setAttribute(RequestAttribute.GENRES, genres);
                request.setAttribute(RequestAttribute.MESSAGE, ADD_BOOK_AVAILABLE_TITLE_MESSAGE);
                return new Router(PagePath.ADD_BOOKS, Router.RouterType.FORWARD);
            }
            bookService.addBook(book);
            List<Genre> genres = genreService.getAllGenres();
            request.setAttribute(RequestAttribute.GENRES, genres);
            request.setAttribute(RequestAttribute.MESSAGE, ADD_BOOK_SUCCESS_MESSAGE);
            return new Router(PagePath.ADD_BOOKS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to add book genre page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
