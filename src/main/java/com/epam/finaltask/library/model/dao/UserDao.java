package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class UserDao extends BaseDao<Integer, User> {

    abstract public Optional<User> findUserByLogin(String login) throws DaoException;

    abstract public Optional<User> findUserByPassword(String password) throws DaoException;

    abstract public Optional<User> findUserByEmail(String email) throws DaoException;

    abstract public List<User> findUsersByStatus(User user, int startElementNumber) throws DaoException;

    abstract public List<User> findUsersByRole(UserRole userRole) throws DaoException;

    abstract public List<User> findUsersByRole(UserRole userRole, int startElementNumber) throws DaoException;


    public abstract Optional<String> findUserPassword(String login) throws DaoException;

    abstract public boolean updateUserPassword(String password, String login) throws DaoException;
}
