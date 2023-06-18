package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.AbstractEntity;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;


public abstract class BaseDao<K, T extends AbstractEntity> {
    protected Logger LOGGER = LogManager.getLogger();

    protected Connection connection;

    abstract public int add(T t) throws DaoException;

    abstract public boolean update(T t) throws DaoException;

    abstract public boolean delete(K k) throws DaoException;

    abstract public List<T> findAll() throws DaoException;

    abstract public Optional<T> findById(K k) throws DaoException;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection(){
        ConnectionPool.getInstance().releaseConnection(connection);
    }

}
