package com.epam.finaltask.library.model.service.impl;

import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.dao.DaoProvider;
import com.epam.finaltask.library.model.dao.GenreDao;
import com.epam.finaltask.library.model.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class GenreServiceImpl implements GenreService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final GenreService instance = new GenreServiceImpl();

    public GenreServiceImpl() {
    }

    public static GenreService getInstance() {
        return instance;
    }

    @Override
    public boolean addGenre(String name) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        GenreDao genreDao = daoProvider.getGenreDao(false);
        name = name.trim();
        if (!isNameOccupied(name)) {
            Genre genre = new Genre();
            genre.setName(name);
            try {
                genreDao.add(genre);
                return true;
            } catch (DaoException exception) {
                LOGGER.error("Error has occurred while inserting genre: " + exception);
                throw new ServiceException("Error has occurred while inserting genre: ", exception);
            } finally {
                genreDao.closeConnection();
            }
        }
        return false;
    }

    @Override
    public boolean editGenre(Genre genre) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        GenreDao genreDao = daoProvider.getGenreDao(false);
        try {
            Optional<Genre> g = genreDao.findGenreByName(genre.getName());
            if (g.isEmpty()) {
                genreDao.update(genre);
                return true;
            } else {
                return false;
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while updating genre: " + exception);
            throw new ServiceException("Error has occurred while updating genre: ", exception);
        } finally {
            genreDao.closeConnection();
        }
    }

    @Override
    public boolean isNameOccupied(String name) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        GenreDao genreDao = daoProvider.getGenreDao(false);

        try {
            Optional<Genre> genre = genreDao.findGenreByName(name);
            return genre.isPresent();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking name availability: " + exception);
            throw new ServiceException("Error has occurred while checking name availability: ", exception);
        } finally {
            genreDao.closeConnection();
        }
    }

    @Override
    public List<Genre> getAllGenres() throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        GenreDao genreDao = daoProvider.getGenreDao(false);
        List<Genre> genres;

        try {
            genres = genreDao.findAll();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all genres: " + exception);
            throw new ServiceException("Error has occurred while finding all genres: ", exception);
        } finally {
            genreDao.closeConnection();
        }
        return genres;
    }
}
