package com.epam.finaltask.library.model.dao.impl;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.dao.BookDao;
import com.epam.finaltask.library.model.dao.mapper.impl.BookMapper;
import com.epam.finaltask.library.model.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl extends BookDao {

    private static final String SQL_INSERT_BOOK = "INSERT INTO books (title, genre_id, author, description, book_copies) " +
            "values (?,?,?,?,?)";

    private static final String SQL_SELECT_BOOKS_BY_TITLE =
            "SELECT books.*,genres.id as genre_id, genres.name as name FROM books left join genres on books.genre_id = genres.id WHERE title = ?";

    private static final String SQL_SELECT_BOOK_BY_ID =
            "SELECT books.*,genres.id as genre_id, genres.name as name FROM books left join genres on books.genre_id = genres.id WHERE books.id = ? LIMIT 1";

    private static final String SQL_SELECT_BOOKS =
            "SELECT books.*,genres.id as genre_id, genres.name as name FROM books left join genres on books.genre_id = genres.id LIMIT 12 OFFSET ?";

    private static final String SQL_SELECT_BOOKS_BY_GENRE =
            "SELECT books.*,genres.id as genre_id, genres.name as name FROM books left join genres on books.genre_id = genres.id WHERE genres.id = ? LIMIT 12 OFFSET ?";

    public BookDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public int add(Book book) throws DaoException {
        try (PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, book.getTitle());
            prepareStatement.setInt(2, book.getGenre().getId());
            prepareStatement.setString(3, book.getAuthor());
            prepareStatement.setString(4, book.getDescription());
            prepareStatement.setInt(5, book.getBookCopies());
            prepareStatement.execute();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while adding book: " + sqlException);
            throw new DaoException("Error has occurred while adding book: ", sqlException);
        }
    }

    @Override
    public boolean update(Book book) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws DaoException {
        return false;
    }

    @Override
    public List<Book> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Book> findById(Integer id) throws DaoException {
        List<Book> books;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BOOK_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            BookMapper bookMapper = BookMapper.getInstance();
            books = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding user by id: " + sqlException);
            throw new DaoException("Error has occurred while finding user by id: ", sqlException);
        }
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public Optional<Book> findBookByTitle(String title) throws DaoException {
        List<Book> books;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BOOKS_BY_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            BookMapper bookMapper = BookMapper.getInstance();
            books = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding books by title: " + sqlException);
            throw new DaoException("Error has occurred while finding books by title: ", sqlException);
        }
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public List<Book> getBooksByGenre(int genre_id, int startElementNumber) throws DaoException {
        List<Book> books;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BOOKS_BY_GENRE)) {
            preparedStatement.setInt(1, genre_id);
            preparedStatement.setInt(2, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            books = BookMapper.getInstance().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding books by genre: " + sqlException);
            throw new DaoException("Error has occurred while finding books by genre: ", sqlException);
        }
        return books;
    }

    @Override
    public List<Book> getBooks(int startElementNumber) throws DaoException {
        List<Book> books;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BOOKS)) {
            preparedStatement.setInt(1, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            books = BookMapper.getInstance().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding books: " + sqlException);
            throw new DaoException("Error has occurred while finding books: ", sqlException);
        }
        return books;
    }
}