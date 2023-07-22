package com.epam.finaltask.library.model.service.impl;

import com.epam.finaltask.library.controller.command.RequestParameter;
import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Image;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.dao.*;
import com.epam.finaltask.library.model.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
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
    public boolean updateBook(Map<String, String> bookMap) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        BookDao bookDao = daoProvider.getBookDao(false);
        GenreDao genreDao = daoProvider.getGenreDao(false);
        try {
            Book book = new Book();
            book.setId(Integer.parseInt(bookMap.get(RequestParameter.BOOK_ID)));
            book.setBookCopies(Integer.parseInt(bookMap.get(RequestParameter.BOOK_COPIES)));
            book.setGenre(genreDao.findById(Integer.parseInt(bookMap.get(RequestParameter.BOOK_GENRE_ID))).get());
            book.setAuthor(bookMap.get(RequestParameter.BOOK_AUTHOR));
            book.setTitle(bookMap.get(RequestParameter.BOOK_TITLE));
            book.setDescription(bookMap.get(RequestParameter.BOOK_DESCRIPTION));
            bookDao.update(book);
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while updating the book: " + exception);
            throw new ServiceException("Error has occurred while updating the book: ", exception);
        } finally {
            bookDao.closeConnection();
            genreDao.closeConnection();
        }
    }

    @Override
    public boolean replaceBookImage(int book_id, String newImageUrl) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        ImageDao imageDao = daoProvider.getImageDao(false);
        try {
            List<Image> images = imageDao.getImagesByBook(book_id);
            Optional<Image> image = images.size() > 0 ? Optional.of(images.get(0)) : Optional.empty();
            if (image.isPresent()){
                image.get().setUrl(newImageUrl);
                imageDao.update(image.get());
            } else {
                imageDao.add(new Image(book_id,newImageUrl));
            }
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while replacing book image: " + exception);
            throw new ServiceException("Error has occurred while replacing book image: ", exception);
        } finally {
            imageDao.closeConnection();
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
    public boolean isBookTitleOccupied(String title, int book_id) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        BookDao bookDao = daoProvider.getBookDao(false);
        try {
            Optional<Book> book = bookDao.findBookByTitle(title, book_id);
            return book.isPresent();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking title availability by id: " + exception);
            throw new ServiceException("Error has occurred while checking title availability bt id: ", exception);
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
    public List<Book> searchBooksByGenreAndSearchQuery(int genre_id, String search_text, int page) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        BookDao bookDao = daoProvider.getBookDao(false);
        ImageDao imageDao = daoProvider.getImageDao(false);
        int startElementNumber = page * 15 - 15;
        List<Book> books = null;
        try {
            if (genre_id == 0 && search_text == null) {
                books = bookDao.getBooks(startElementNumber);
            } else if (genre_id == 0) {
                books = bookDao.getBooksBySearchQuery(search_text, startElementNumber);
            } else {
                books = bookDao.getBooksByGenre(genre_id, startElementNumber);
            }
            for (Book book : books) {
                List<Image> images = imageDao.getImagesByBook(book.getId());
                book.setImages(images);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding books by genre and search text " + exception);
            throw new ServiceException("Error has occurred while finding books by genre and search text ", exception);
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
