package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.entity.enums.UserStatus;
import com.epam.finaltask.library.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class UserDao extends BaseDao<Integer, User> {

    abstract public Optional<User> findUserByLogin(String login) throws DaoException;

    abstract public Optional<User> findUserByEmail(String email) throws DaoException;

    abstract public Optional<User> findUserByPassport(String passport) throws DaoException;

    abstract public List<User> findUsersByRole(UserRole userRole) throws DaoException;

    public abstract Optional<String> findUserPassword(String login) throws DaoException;

    abstract public boolean updateUserPassword(String password, String login) throws DaoException;

    abstract public List<User> getUsersByRoleAndStatus(UserRole role, UserStatus status, int startElementNumber) throws DaoException;

    abstract public List<User> getUsersByRole(UserRole role, int startElementNumber) throws DaoException;

    abstract public List<User> searchUsersByRoleAndStatusAndQuery(UserRole role, UserStatus userStatus, String search, int startElementNumber) throws DaoException;

    abstract public List<User> searchUsersByRoleAndQuery(UserRole role, String search, int startElementNumber) throws DaoException;

    abstract public List<User> searchUsersByRoleAndStatus(UserRole role, UserStatus userStatus, int startElementNumber) throws DaoException;

    abstract public boolean changeUserStatus(int id, UserStatus status) throws DaoException;

    abstract public int userCountsByRole(UserRole userRole) throws DaoException;
}
