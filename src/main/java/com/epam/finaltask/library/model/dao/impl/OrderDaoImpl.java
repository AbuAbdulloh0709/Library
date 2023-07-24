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

    private static final String SQL_SELECT_ISSUED_BOOKS = "SELECT * from view_orders as orders where orders.last_status = 'accepted' LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_REQUESTED_BOOKS = "SELECT * from view_orders as orders where orders.last_status = 'created' LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_ORDER_HISTORY = "SELECT * from view_orders as orders where orders.last_status = 'returned' or orders.last_status = 'canceled' LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_ORDER_HISTORY_WITHOUT_PAGING = "SELECT * from view_orders as orders where orders.last_status = 'returned' or orders.last_status = 'canceled' ";

    private static final String SQL_SELECT_ORDERS_BY_ORDER_TYPE = "SELECT * from view_orders as orders where orders.last_status = 'accepted' and orders.type = ? LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_ORDERS_BY_ORDER_TYPE_AND_SEARCH_QUERY = "SELECT * from view_orders as orders where orders.last_status = 'accepted' and orders.type = ? and (first_name like '%' || ? || '%' or last_name like '%' || ? || '%' or title like '%' || ? || '%') LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_ORDERS_BY_SEARCH_QUERY = "SELECT * from view_orders as orders where orders.last_status = 'accepted' and (first_name like '%' || ? || '%' or last_name like '%' || ? || '%' or title like '%' || ? || '%') LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_REQUESTS_BY_SEARCH_QUERY = "SELECT * from view_orders as orders where orders.last_status = 'created' and (first_name like '%' || ? || '%' or last_name like '%' || ? || '%' or title like '%' || ? || '%') LIMIT 15 OFFSET ?";

    private static final String SQL_SELECT_ORDER_HISTORY_BY_SEARCH_QUERY = "SELECT * from view_orders as orders where (orders.last_status = 'returned' or orders.last_status = 'canceled') and (first_name like '%' || ? || '%' or last_name like '%' || ? || '%' or title like '%' || ? || '%')";

    private static final String SQL_SELECT_ORDER_HISTORY_BY_SEARCH_QUERY_AND_USER_ID = "SELECT * from view_orders as orders where (orders.last_status = 'returned' or orders.last_status = 'canceled') and (first_name like '%' || ? || '%' or last_name like '%' || ? || '%' or title like '%' || ? || '%') and orders.order_user_id=?";

    private static final String SQL_SELECT_ORDER_HISTORY_BY_DATE = "SELECT * from view_orders as orders where (orders.last_status = 'returned' or orders.last_status = 'canceled') and (issue_date between ? and ?)";

    private static final String SQL_SELECT_ORDER_HISTORY_BY_DATE_AND_USER_ID = "SELECT * from view_orders as orders where (orders.last_status = 'returned' or orders.last_status = 'canceled') and (issue_date between ? and ?) and orders.order_user_id=?";

    private static final String SQL_SELECT_ORDER_HISTORY_BY_SEARCH_QUERY_AND_DATE = "SELECT * from view_orders as orders where (orders.last_status = 'returned' or orders.last_status = 'canceled') and (issue_date between ? and ?) and (first_name like '%' || ? || '%' or last_name like '%' || ? || '%' or title like '%' || ? || '%')";

    private static final String SQL_SELECT_ORDER_HISTORY_BY_SEARCH_QUERY_AND_DATE_AND_USER_ID = "SELECT * from view_orders as orders where (orders.last_status = 'returned' or orders.last_status = 'canceled') and (issue_date between ? and ?) and (first_name like '%' || ? || '%' or last_name like '%' || ? || '%' or title like '%' || ? || '%') and orders.order_user_id=?";

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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ISSUED_BOOKS)) {
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
    public List<Order> requestedBooks(int startElementNumber) throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_REQUESTED_BOOKS)) {
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
    public List<Order> orderHistory() throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_HISTORY_WITHOUT_PAGING)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            OrderMapper bookMapper = OrderMapper.getInstance();
            orders = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding order history: " + sqlException);
            throw new DaoException("Error has occurred while finding order history: ", sqlException);
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
    public List<Order> searchRequestedBooksByQuery(String searchQuery, int startElementNumber) throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_REQUESTS_BY_SEARCH_QUERY)) {
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            preparedStatement.setString(3, searchQuery);
            preparedStatement.setInt(4, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            OrderMapper bookMapper = OrderMapper.getInstance();
            orders = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding requests by search query: " + sqlException);
            throw new DaoException("Error has occurred while finding requests by search query: ", sqlException);
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

    @Override
    public List<Order> searchHistoryByQuery(String searchQuery) throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_HISTORY_BY_SEARCH_QUERY)) {
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            preparedStatement.setString(3, searchQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            OrderMapper bookMapper = OrderMapper.getInstance();
            orders = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding order history by search query: " + sqlException);
            throw new DaoException("Error has occurred while finding order history by search query: ", sqlException);
        }
        return orders;
    }

     @Override
     public List<Order> searchHistoryByQuery(int id, String searchQuery) throws DaoException {
         List<Order> orders;
         try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_HISTORY_BY_SEARCH_QUERY_AND_USER_ID)) {
             preparedStatement.setString(1, searchQuery);
             preparedStatement.setString(2, searchQuery);
             preparedStatement.setString(3, searchQuery);
             preparedStatement.setInt(4, id);
             ResultSet resultSet = preparedStatement.executeQuery();
             OrderMapper bookMapper = OrderMapper.getInstance();
             orders = bookMapper.retrieve(resultSet);
         } catch (SQLException sqlException) {
             LOGGER.error("Error has occurred while finding order history by search query and order user id: " + sqlException);
             throw new DaoException("Error has occurred while finding order history by search query and order user id: ", sqlException);
         }
         return orders;
     }

     @Override
    public List<Order> searchHistoryByDate(String from, String to) throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_HISTORY_BY_DATE)) {
            preparedStatement.setDate(1, Date.valueOf(from));
            preparedStatement.setDate(2, Date.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();
            OrderMapper bookMapper = OrderMapper.getInstance();
            orders = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding order history by date: " + sqlException);
            throw new DaoException("Error has occurred while finding order history by date: ", sqlException);
        }
        return orders;
    }

     @Override
     public List<Order> searchHistoryByDate(int id, String from, String to) throws DaoException {
         List<Order> orders;
         try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_HISTORY_BY_DATE_AND_USER_ID)) {
             preparedStatement.setDate(1, Date.valueOf(from));
             preparedStatement.setDate(2, Date.valueOf(to));
             preparedStatement.setInt(3, id);
             ResultSet resultSet = preparedStatement.executeQuery();
             OrderMapper bookMapper = OrderMapper.getInstance();
             orders = bookMapper.retrieve(resultSet);
         } catch (SQLException sqlException) {
             LOGGER.error("Error has occurred while finding order history by date and user id: " + sqlException);
             throw new DaoException("Error has occurred while finding order history by date and user id: ", sqlException);
         }
         return orders;
     }

     @Override
    public List<Order> searchHistoryByDateAndQuery(String from, String to, String search_text) throws DaoException {
        List<Order> orders;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_HISTORY_BY_SEARCH_QUERY_AND_DATE)) {
            preparedStatement.setDate(1, Date.valueOf(from));
            preparedStatement.setDate(2, Date.valueOf(to));
            preparedStatement.setString(3, search_text);
            preparedStatement.setString(4, search_text);
            preparedStatement.setString(5, search_text);
            ResultSet resultSet = preparedStatement.executeQuery();
            OrderMapper bookMapper = OrderMapper.getInstance();
            orders = bookMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding order history by date and search query: " + sqlException);
            throw new DaoException("Error has occurred while finding order history by date and search query: ", sqlException);
        }
        return orders;
    }

     @Override
     public List<Order> searchHistoryByDateAndQuery(int id, String from, String to, String search_text) throws DaoException {
         List<Order> orders;
         try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_HISTORY_BY_SEARCH_QUERY_AND_DATE_AND_USER_ID)) {
             preparedStatement.setDate(1, Date.valueOf(from));
             preparedStatement.setDate(2, Date.valueOf(to));
             preparedStatement.setString(3, search_text);
             preparedStatement.setString(4, search_text);
             preparedStatement.setString(5, search_text);
             preparedStatement.setInt(6, id);
             ResultSet resultSet = preparedStatement.executeQuery();
             OrderMapper bookMapper = OrderMapper.getInstance();
             orders = bookMapper.retrieve(resultSet);
         } catch (SQLException sqlException) {
             LOGGER.error("Error has occurred while finding order history by date and search query and user id: " + sqlException);
             throw new DaoException("Error has occurred while finding order history by date and search query and user id: ", sqlException);
         }
         return orders;
     }
 }
