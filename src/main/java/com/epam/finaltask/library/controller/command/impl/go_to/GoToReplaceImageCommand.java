package com.epam.finaltask.library.controller.command.impl.go_to;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
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

import static com.epam.finaltask.library.controller.command.RequestParameter.BOOK_ID;

public class GoToReplaceImageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService = BookServiceImpl.getInstance();
    private final GenreService genreService = GenreServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        int book_id = Integer.parseInt(request.getParameter(BOOK_ID));
        try {
            Book book = bookService.findById(book_id).get();
            List<Genre> genres = genreService.getAllGenres();
            request.setAttribute(RequestAttribute.BOOK, book);
            request.setAttribute(RequestAttribute.GENRES, genres);
            return new Router(PagePath.REPLACE_BOOK_IMAGE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to replace book image page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
