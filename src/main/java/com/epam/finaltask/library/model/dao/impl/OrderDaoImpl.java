package com.epam.finaltask.library.model.dao.impl;

import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.entity.enums.OrderType;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.dao.OrderDao;
import com.epam.finaltask.library.model.dao.mapper.impl.OrderMapper;
import com.epam.finaltask.library.model.pool.ConnectionPool;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl extends OrderDao {

    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(user_id, book_id, issue_date, return_date, usage_days, type) " +
            "values (?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT * from view_orders as orders where orders.id = ?";

    private static final String SQL_SELECT_ORDERS = "SELECT * from view_orders as orders where orders.last_status = 'accepted' LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_ORDERS_BY_ORDER_TYPE = "SELECT * from view_orders as orders where orders.last_status = 'accepted' and orders.type = ? LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_ORDERS_BY_ORDER_TYPE_AND_SEARCH_QUERY = "SELECT * from view_orders as orders where orders.last_status = 'accepted' and orders.type = ? and (users.first_name like '%' || ? || '%' or users.last_name like '%' || ? || '%' or books.title like '%' || ? || '%') LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_ORDERS_BY_SEARCH_QUERY = "SELECT * from view_orders as orders where orders.last_status = 'accepted' and (users.first_name like '%' || ? || '%' or users.last_name like '%' || ? || '%' or books.title like '%' || ? || '%') LIMIT 15 OFFSET ?";

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

    @Override
    public List<Order> issuedBooks(int startElementNumber) throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDERS)) {
            preparedStatement.setInt(1, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            OrderMapper bookMapper = OrderMapper.getInstance();
            orders = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding orders: " + sqlException);
            throw new DaoException("Error has occurred while finding orders: ", sqlException);
        }
        return orders;
    }

    @Override
    public List<Order> searchIssuedBooksByOrderType(OrderType orderType, int startElementNumber) throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_ORDER_TYPE)) {
            preparedStatement.setString(1, orderType.getType());
            preparedStatement.setInt(2, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            OrderMapper bookMapper = OrderMapper.getInstance();
            orders = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding orders by order type: " + sqlException);
            throw new DaoException("Error has occurred while finding orders by order type: ", sqlException);
        }
        return orders;
    }

    @Override
    public List<Order> searchIssuedBooksByQuery(String searchQuery, int startElementNumber) throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_SEARCH_QUERY)) {
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            preparedStatement.setString(3, searchQuery);
            preparedStatement.setInt(4, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            OrderMapper bookMapper = OrderMapper.getInstance();
            orders = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding orders by search query: " + sqlException);
            throw new DaoException("Error has occurred while finding orders by search query: ", sqlException);
        }
        return orders;
    }

    @Override
    public List<Order> searchIssuedBooksByOrderTypeAndQuery(OrderType orderType, String searchQuery, int startElementNumber) throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_ORDER_TYPE_AND_SEARCH_QUERY)) {
            preparedStatement.setString(1, orderType.getType());
            preparedStatement.setString(2, searchQuery);
            preparedStatement.setString(3, searchQuery);
            preparedStatement.setString(4, searchQuery);
            preparedStatement.setInt(5, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            OrderMapper bookMapper = OrderMapper.getInstance();
            orders = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding orders by order type and search query: " + sqlException);
            throw new DaoException("Error has occurred while finding orders by order type and search query: ", sqlException);
        }
        return orders;
    }
}
