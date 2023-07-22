package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.entity.enums.OrderType;
import com.epam.finaltask.library.exception.DaoException;

import java.util.List;

public abstract class OrderDao extends BaseDao<Integer, Order> {
    public abstract List<Order> issuedBooks(int startElementNumber) throws DaoException;

    public abstract List<Order> requestedBooks(int startElementNumber) throws DaoException;

    public abstract List<Order> orderHistory(int startElementNumber) throws DaoException;

    public abstract List<Order> orderHistory() throws DaoException;

    public abstract List<Order> searchIssuedBooksByOrderType(OrderType orderType, int startElementNumber) throws DaoException;

    public abstract List<Order> searchIssuedBooksByQuery(String searchQuery, int startElementNumber) throws DaoException;

    public abstract List<Order> searchRequestedBooksByQuery(String searchQuery, int startElementNumber) throws DaoException;

    public abstract List<Order> searchIssuedBooksByOrderTypeAndQuery(OrderType orderType, String searchQuery, int startElementNumber) throws DaoException;

    public abstract List<Order> searchHistoryByQuery(String searchQuery) throws DaoException;

    public abstract List<Order> searchHistoryByDate(String from, String to) throws DaoException;

    public abstract List<Order> searchHistoryByDateAndQuery(String from, String to, String search_text) throws DaoException;
}
