package com.epam.finaltask.library.model.dao;


import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.pool.ConnectionPool;
import org.apache.logging.log4j.*;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Transaction instance = new Transaction();
    private Connection connection;

    private Transaction() {

    }

    public static Transaction getInstance() {
        return instance;
    }

    public void begin(BaseDao... daos) throws DaoException {
        if (connection == null) {
            connection = ConnectionPool.getInstance().getConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while beginning a transaction: " + sqlException);
            throw new DaoException("Error has occurred while beginning a transaction: ", sqlException);
        }

        for (BaseDao dao : daos) {
            dao.setConnection(connection);
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while doing transaction commit: " + sqlException);
            throw new DaoException("Error has occurred while doing transaction commit: ", sqlException);
        }
    }

    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while doing transaction rollback: " + sqlException);
            throw new DaoException("Error has occurred while doing transaction rollback: ");
        }
    }

    public void end() throws DaoException {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
                connection.setAutoCommit(true);

            } catch (SQLException sqlException) {
                LOGGER.error("Error has occurred while ending transaction rollback: " + sqlException);
                throw new DaoException("Error has occurred while ending transaction rollback: ");
            } finally {
                connection = null;
            }
        }
    }

}
