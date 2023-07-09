package com.epam.finaltask.library.model.dao.impl;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.dao.OrderDao;
import com.epam.finaltask.library.model.dao.mapper.impl.BookMapper;
import com.epam.finaltask.library.model.dao.mapper.impl.OrderMapper;
import com.epam.finaltask.library.model.pool.ConnectionPool;
import com.epam.finaltask.library.util.StringToLocalDateConverter;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl extends OrderDao {

    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(user_id, book_id, issue_date, return_date, usage_days, type) " +
            "values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT *, orders.id order_id, users.created_at user_created_at, books.created_at book_created_at from orders join users on orders.user_id = users.id join\n" +
            "    (SELECT books.*, genres.name as name FROM books left join genres on books.genre_id = genres.id)\n" +
            "    as books on books.id = orders.book_id where orders.id = ?";

    public OrderDaoImpl() {
    }

    public OrderDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public int add(Order order) throws DaoException {
        try (PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setInt(1, order.getUser().getId());
            prepareStatement.setInt(2, order.getBook().getId());
            prepareStatement.setDate(3, Date.valueOf(order.getIssueDate()));
            prepareStatement.setDate(4, Date.valueOf(order.getReturnDate()));
            prepareStatement.setInt(5, order.getUsageDays());
            prepareStatement.setString(6, order.getOrderType().getType());
            prepareStatement.execute();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while creating order: " + sqlException);
            throw new DaoException("Error has occurred while creating order: ", sqlException);
        }
    }

    @Override
    public boolean update(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws DaoException {
        return false;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Order> findById(Integer id) throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            OrderMapper bookMapper = OrderMapper.getInstance();
            orders = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding order by id: " + sqlException);
            throw new DaoException("Error has occurred while finding order by id: ", sqlException);
        }
        return orders.isEmpty() ? Optional.empty() : Optional.of(orders.get(0));
    }
}
