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

    private static final String SQL_UPDATE_BOOK = "UPDATE books set title=?, genre_id=?, author=?, description=?, book_copies=? WHERE id=?";

    private static final String SQL_SELECT_BOOKS_BY_TITLE =
            "SELECT books.*,genres.id as genre_id, genres.name as name,bac.id as bac_id, bac.available_copies FROM books " +
                    "join genres on books.genre_id = genres.id " +
                    "join book_available_copies bac on books.id = bac.book_id WHERE title = ?";
    private static final String SQL_SELECT_BOOKS_BY_TITLE_AND_ID =
            "SELECT books.*,genres.id as genre_id, genres.name as name,bac.id as bac_id, bac.available_copies FROM books " +
                    "join genres on books.genre_id = genres.id " +
                    "join book_available_copies bac on books.id = bac.book_id WHERE title = ? and books.id!=?";

    private static final String SQL_SELECT_BOOK_BY_ID =
            "SELECT books.*,genres.id as genre_id, genres.name as name,bac.id as bac_id, bac.available_copies FROM books " +
                    "join genres on books.genre_id = genres.id " +
                    "join book_available_copies bac on books.id = bac.book_id WHERE books.id = ? LIMIT 1";

    private static final String SQL_SELECT_BOOKS =
            "SELECT books.*,genres.id as genre_id, genres.name as name,bac.id as bac_id, bac.available_copies FROM books " +
                    "join genres on books.genre_id = genres.id " +
                    "join book_available_copies bac on books.id = bac.book_id LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_BOOKS_BY_GENRE =
            "SELECT books.*,genres.id as genre_id, genres.name as name,bac.id as bac_id, bac.available_copies FROM books " +
                    "join genres on books.genre_id = genres.id " +
                    "join book_available_copies bac on books.id = bac.book_id WHERE genres.id = ? LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_BOOKS_BY_SEARCH_QUERY =
            "SELECT books.*,genres.id as genre_id, genres.name as name,bac.id as bac_id, bac.available_copies FROM books " +
                    "join genres on books.genre_id = genres.id " +
                    "join book_available_copies bac on books.id = bac.book_id WHERE (title like '%' || ? || '%') LIMIT 15 OFFSET ?";
    private static final String SQL_SELECT_BOOKS_BY_GENRE_AND_SEARCH_QUERY =
            "SELECT books.*,genres.id as genre_id, genres.name as name,bac.id as bac_id, bac.available_copies FROM books " +
                    "join genres on books.genre_id = genres.id " +
                    "join book_available_copies bac on books.id = bac.book_id WHERE genres.id = ? and (title like '%' || ? || '%') LIMIT 15 OFFSET ?";

    private static final String SQL_BOOK_COUNTS = "Select count(*) as count from books";

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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BOOK)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getGenre().getId());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getDescription());
            preparedStatement.setInt(5, book.getBookCopies());
            preparedStatement.setInt(6, book.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while updating book: " + sqlException);
            throw new DaoException("Error has occurred while updating book: ", sqlException);
        }
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
    public Optional<Book> findBookByTitle(String title, int id) throws DaoException {
        List<Book> books;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BOOKS_BY_TITLE_AND_ID)) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, id);
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
    public List<Book> getBooksBySearchQuery(String search_text, int startElementNumber) throws DaoException {
        List<Book> books;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BOOKS_BY_SEARCH_QUERY)) {
            preparedStatement.setString(1, search_text);
            preparedStatement.setInt(2, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            books = BookMapper.getInstance().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding books by search query: " + sqlException);
            throw new DaoException("Error has occurred while finding books by search query: ", sqlException);
        }
        return books;
    }

    @Override
    public List<Book> getBooksByGenreAndSearchQuery(int genre_id, String search_text, int startElementNumber) throws DaoException {
        List<Book> books;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BOOKS_BY_GENRE_AND_SEARCH_QUERY)) {
            preparedStatement.setInt(1, genre_id);
            preparedStatement.setString(2, search_text);
            preparedStatement.setInt(3, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            books = BookMapper.getInstance().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding books by genre and search query: " + sqlException);
            throw new DaoException("Error has occurred while finding books by genre and search query: ", sqlException);
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

    @Override
    public int getBookCounts() throws DaoException {
        int count = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BOOK_COUNTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding books counts: " + sqlException);
            throw new DaoException("Error has occurred while finding books counts: ", sqlException);
        }
        return count;
    }
}