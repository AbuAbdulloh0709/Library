package com.epam.finaltask.library.controller.command.impl.go_to;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
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

public class GoToAddBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final GenreService genreService = GenreServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        List<Genre> genres;
        try {
            genres = genreService.getAllGenres();
            request.setAttribute(RequestAttribute.GENRES, genres);
            return new Router(PagePath.ADD_BOOKS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to add book genre page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
