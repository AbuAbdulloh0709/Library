package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.entity.enums.OrderType;
import com.epam.finaltask.library.exception.DaoException;

import java.util.List;

public abstract class OrderDao extends BaseDao<Integer, Order> {
    public abstract List<Order> issuedBooks(int startElementNumber) throws DaoException;

    public abstract List<Order> searchIssuedBooksByOrderType(OrderType orderType, int startElementNumber) throws DaoException;

    public abstract List<Order> searchIssuedBooksByQuery(String searchQuery, int startElementNumber) throws DaoException;

    public abstract List<Order> searchIssuedBooksByOrderTypeAndQuery(OrderType orderType, String searchQuery, int startElementNumber) throws DaoException;
}
