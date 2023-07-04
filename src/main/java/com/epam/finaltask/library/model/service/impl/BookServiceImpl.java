package com.epam.finaltask.library.model.service.impl;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.dao.BookDao;
import com.epam.finaltask.library.model.dao.DaoProvider;
import com.epam.finaltask.library.model.dao.GenreDao;
import com.epam.finaltask.library.model.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final BookServiceImpl instance = new BookServiceImpl();

    public BookServiceImpl() {
    }

    public static BookServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addBook(Book book) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        BookDao bookDao = daoProvider.getBookDao(false);
        try {
            bookDao.add(book);
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while inserting book: " + exception);
            throw new ServiceException("Error has occurred while inserting book: ", exception);
        } finally {
            bookDao.closeConnection();
        }
    }

    @Override
    public boolean isBookTitleOccupied(String title) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        BookDao bookDao = daoProvider.getBookDao(false);
        try {
            Optional<Book> book = bookDao.findBookByTitle(title);
            return book.isPresent();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking title availability: " + exception);
            throw new ServiceException("Error has occurred while checking title availability: ", exception);
        } finally {
            bookDao.closeConnection();
        }
    }

    @Override
    public List<Book> getAllBooks(int page) throws ServiceException {
        return null;
    }
}
