package com.epam.finaltask.library.model.dao.impl;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.dao.GenreDao;
import com.epam.finaltask.library.model.dao.mapper.impl.BookMapper;
import com.epam.finaltask.library.model.dao.mapper.impl.GenreMapper;
import com.epam.finaltask.library.model.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class GenreDaoImpl extends GenreDao {

    private static final String SQL_INSERT_GENRE = "INSERT INTO genres(name) " +
            "values (?)";
    private static final String SQL_SELECT_GENRES_BY_ID =
            "SELECT * FROM genres WHERE id = ?";
    private static final String SQL_SELECT_GENRES_BY_NAME =
            "SELECT * FROM genres WHERE name = ?";
    private static final String SQL_SELECT_GENRES =
            "SELECT * FROM genres order by id";
    private static final String SQL_UPDATE_GENRE = "UPDATE genres set name = ? where id = ?";

    public GenreDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public int add(Genre genre) throws DaoException {
        try (PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT_GENRE, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, genre.getName());
            prepareStatement.execute();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while adding genre: " + sqlException);
            throw new DaoException("Error has occurred while adding genre: ", sqlException);
        }
    }

    @Override
    public boolean update(Genre genre) throws DaoException {
        try (PreparedStatement prepareStatement = connection.prepareStatement(SQL_UPDATE_GENRE)) {
            prepareStatement.setString(1, genre.getName());
            prepareStatement.setInt(2, genre.getId());
            prepareStatement.execute();
            return true;
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while updating genre: " + sqlException);
            throw new DaoException("Error has occurred while updating genre: ", sqlException);
        }
    }

    @Override
    public boolean delete(Integer integer) throws DaoException {
        return false;
    }

    @Override
    public List<Genre> findAll() throws DaoException {
        List<Genre> genres;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_GENRES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            GenreMapper genreMapper = GenreMapper.getInstance();
            genres = genreMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding genre by name: " + sqlException);
            throw new DaoException("Error has occurred while finding genre by name: ", sqlException);
        }
        return genres;
    }

    @Override
    public Optional<Genre> findById(Integer id) throws DaoException {
        List<Genre> genres;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_GENRES_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            GenreMapper genreMapper = GenreMapper.getInstance();
            genres = genreMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding genre by id: " + sqlException);
            throw new DaoException("Error has occurred while finding genre by id: ", sqlException);
        }
        return genres.isEmpty() ? Optional.empty() : Optional.of(genres.get(0));
    }

    @Override
    public Optional<Genre> findGenreByName(String name) throws DaoException {
        List<Genre> genres;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_GENRES_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            GenreMapper genreMapper = GenreMapper.getInstance();
            genres = genreMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding genre by name: " + sqlException);
            throw new DaoException("Error has occurred while finding genre by name: ", sqlException);
        }
        return genres.isEmpty() ? Optional.empty() : Optional.of(genres.get(0));
    }
}
