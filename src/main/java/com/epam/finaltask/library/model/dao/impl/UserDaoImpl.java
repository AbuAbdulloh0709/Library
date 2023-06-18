package com.epam.finaltask.library.model.dao.impl;

import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.dao.UserDao;
import com.epam.finaltask.library.model.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends UserDao {

    private static final String SQL_INSERT_USER = "INSERT INTO users(login, first_name, last_name, passport_number, email, role, address, birth_date, status) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";


    public UserDaoImpl(){}

    public UserDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public int add(User user) throws DaoException {
        try (PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, user.getLogin());
            prepareStatement.setString(2, user.getFirstName());
            prepareStatement.setString(3, user.getLastName());
            prepareStatement.setString(4, user.getPassportNumber());
            prepareStatement.setString(5, user.getEmail());
            prepareStatement.setString(6, user.getRole().getRole());
            prepareStatement.setString(7, user.getAddress());
            prepareStatement.setString(8, user.getBirthDate());
            prepareStatement.setString(9, user.getStatus());
            prepareStatement.execute();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while adding user: " + sqlException);
            throw new DaoException("Error has occurred while adding user: ", sqlException);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws DaoException {
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByPassword(String password) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findUsersByStatus(User user, int startElementNumber) throws DaoException {
        return null;
    }

    @Override
    public List<User> findUsersByRole(UserRole userRole) throws DaoException {
        return null;
    }

    @Override
    public List<User> findUsersByRole(UserRole userRole, int startElementNumber) throws DaoException {
        return null;
    }
}
