package com.epam.finaltask.library.model.dao.impl;

import com.epam.finaltask.library.entity.OrderDetail;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.dao.OrderDetailDao;
import com.epam.finaltask.library.model.dao.mapper.impl.OrderDetailMapper;
import com.epam.finaltask.library.model.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class OrderDetailDaoImpl extends OrderDetailDao {

    private static final String SQL_INSERT_ORDER_DETAIL = "INSERT INTO order_details(order_id, user_id, status, comment) " +
            "values (?,?,?,?)";

    private static final String SQL_ORDER_DETAILS = "Select * from view_order_details where order_id = ?";

    public OrderDetailDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }


    @Override
    public int add(OrderDetail orderDetail) throws DaoException {
        try (PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT_ORDER_DETAIL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setInt(1, orderDetail.getOrderId());
            prepareStatement.setInt(2, orderDetail.getUser().getId());
            prepareStatement.setString(3, orderDetail.getOrderDetailStatus().getStatus());
            prepareStatement.setString(4, orderDetail.getComment());
            prepareStatement.execute();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while creating order detail: " + sqlException);
            throw new DaoException("Error has occurred while creating order detail: ", sqlException);
        }
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws DaoException {
        return false;
    }

    @Override
    public List<OrderDetail> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<OrderDetail> findById(Integer integer) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(int order_id) throws DaoException {
        List<OrderDetail> orderDetails = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ORDER_DETAILS)) {
            preparedStatement.setInt(1, order_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            orderDetails = new OrderDetailMapper().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding order details by order id: " + sqlException);
            throw new DaoException("Error has occurred while finding order details by order id: ", sqlException);
        }
        return orderDetails;
    }
}
