package com.epam.finaltask.library.model.service;

import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.entity.enums.OrderType;
import com.epam.finaltask.library.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {

    boolean createOrder(Map<String, String> orderMap) throws ServiceException;

    List<Order> issuedBooks(int page) throws ServiceException;

    List<Order> orderHistory(int page) throws ServiceException;

    List<Order> searchOrderHistory(String from, String to, String search_text) throws ServiceException;

    List<Order> requestedBooks(int page) throws ServiceException;

    List<Order> searchIssuedBooksByOrderTypeAndQuery(OrderType orderType, String search, int page) throws ServiceException;

    List<Order> searchRequestedBooksByQuery(String search, int page) throws ServiceException;

    Optional<Order> findOrderById(int id) throws ServiceException;
}
