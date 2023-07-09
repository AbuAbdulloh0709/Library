package com.epam.finaltask.library.model.dao.impl;

import com.epam.finaltask.library.entity.Image;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.dao.ImageDao;
import com.epam.finaltask.library.model.dao.mapper.impl.ImageMapper;
import com.epam.finaltask.library.model.dao.mapper.impl.UserMapper;
import com.epam.finaltask.library.model.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class ImageDaoImpl extends ImageDao {

    private static final String SQL_INSERT_IMAGE = "INSERT INTO images (book_id, url) " +
            "values (?,?)";

    private static final String SQL_IMAGES_BY_BOOK = "Select * from images where book_id = ?";

    public ImageDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public int add(Image image) throws DaoException {
        try (PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT_IMAGE, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setInt(1, image.getBookId());
            prepareStatement.setString(2, image.getUrl());
            prepareStatement.execute();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while adding image: " + sqlException);
            throw new DaoException("Error has occurred while adding image: ", sqlException);
        }
    }

    @Override
    public boolean update(Image image) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws DaoException {
        return false;
    }

    @Override
    public List<Image> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Image> findById(Integer integer) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Image> getImagesByBook(int book_id) throws DaoException {
        List<Image> images;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_IMAGES_BY_BOOK)) {
            preparedStatement.setInt(1, book_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            images = new ImageMapper().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding images by book: " + sqlException);
            throw new DaoException("Error has occurred while finding images by book: ", sqlException);
        }
        return images;
    }
}
