package com.epam.finaltask.library.model.service.impl;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Image;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.dao.BookDao;
import com.epam.finaltask.library.model.dao.DaoProvider;
import com.epam.finaltask.library.model.dao.ImageDao;
import com.epam.finaltask.library.model.dao.Transaction;
import com.epam.finaltask.library.model.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final BookService instance = new BookServiceImpl();

    public BookServiceImpl() {
    }

    public static BookService getInstance() {
        return instance;
    }

    @Override
    public boolean addBook(Book book, String fileName) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        BookDao bookDao = daoProvider.getBookDao(true);
        ImageDao imageDao = daoProvider.getImageDao(true);
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(bookDao, imageDao);
            int book_id = bookDao.add(book);
            imageDao.add(new Image(book_id, fileName));
            transaction.commit();
            return true;
        } catch (DaoException exception) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while doing transaction rollback for inserting book: " + daoException);
            }
            LOGGER.error("Error has occurred while inserting book: " + exception);
            throw new ServiceException("Error has occurred while inserting book: ", exception);
        } finally {
            try {
                transaction.end();
            } catch (DaoException exception) {
                LOGGER.error("Error has occurred while ending transaction for inserting book: " + exception);
            }
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
    public List<Book> getAllBooksByGenre(int genre_id, int page) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        BookDao bookDao = daoProvider.getBookDao(false);
        ImageDao imageDao = daoProvider.getImageDao(false);
        int startElementNumber = page * 15 - 15;
        List<Book> books = null;
        try {
            books = bookDao.getBooksByGenre(genre_id, startElementNumber);
            for (Book book : books) {
                List<Image> images = imageDao.getImagesByBook(book.getId());
                book.setImages(images);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding books by genre: " + exception);
            throw new ServiceException("Error has occurred while finding books by genre: ", exception);
        } finally {
            bookDao.closeConnection();
            imageDao.closeConnection();
        }

        return books;
    }

    @Override
    public List<Book> getAllBooks(int page) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        BookDao bookDao = daoProvider.getBookDao(false);
        ImageDao imageDao = daoProvider.getImageDao(false);
        int startElementNumber = page * 15 - 15;
        List<Book> books = null;
        try {
            books = bookDao.getBooks(startElementNumber);
            for (Book book : books) {
                List<Image> images = imageDao.getImagesByBook(book.getId());
                book.setImages(images);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding books by page: " + exception);
            throw new ServiceException("Error has occurred while finding books by page: ", exception);
        } finally {
            bookDao.closeConnection();
            imageDao.closeConnection();
        }

        return books;
    }

    @Override
    public Optional<Book> findById(int id) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        BookDao bookDao = daoProvider.getBookDao(false);
        ImageDao imageDao = daoProvider.getImageDao(false);
        try {
            Optional<Book> book = bookDao.findById(id);
            List<Image> images = imageDao.getImagesByBook(id);
            book.get().setImages(images);
            return book;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding a book by id: " + exception);
            throw new ServiceException("Error has occurred while finding a book by id: ", exception);
        } finally {
            bookDao.closeConnection();
        }
    }
}
