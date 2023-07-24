package com.epam.finaltask.library.model.service.impl;

import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.entity.OrderDetail;
import com.epam.finaltask.library.entity.enums.OrderDetailStatus;
import com.epam.finaltask.library.entity.enums.OrderType;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.dao.*;
import com.epam.finaltask.library.model.service.OrderService;
import com.epam.finaltask.library.util.DateDifference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.finaltask.library.controller.command.RequestParameter.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final OrderService instance = new OrderServiceImpl();

    public OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        return instance;
    }

    @Override
    public boolean createOrder(Map<String, String> orderMap) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        OrderDao orderDao = daoProvider.getOrderDao(true);
        UserDao userDao = daoProvider.getUserDao(true);
        BookDao bookDao = daoProvider.getBookDao(true);
        OrderDetailDao orderDetailDao = daoProvider.getOrderDetailDao(true);
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(orderDao, orderDetailDao, userDao, bookDao);
            Order order = new Order();
            order.setUser(userDao.findById(Integer.valueOf(orderMap.get(ORDER_USER_ID))).get());
            order.setBook(bookDao.findById(Integer.valueOf(orderMap.get(BOOK_ID))).get());
            order.setOrderType(OrderType.valueOf(orderMap.get(ORDER_TYPE).toUpperCase()));
            order.setIssueDate(orderMap.get(ISSUE_DATE));
            order.setReturnDate(orderMap.get(RETURN_DATE));
            order.setUsageDays(DateDifference.calculateDateDifference(orderMap.get(ISSUE_DATE), orderMap.get(RETURN_DATE)));
            int order_id = orderDao.add(order);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order_id);
            orderDetail.setUser(userDao.findById(Integer.valueOf(orderMap.get(ORDER_DETAIL_USER_ID))).get());
            orderDetail.setOrderDetailStatus(OrderDetailStatus.valueOf(orderMap.get(ORDER_DETAIL_STATUS).toUpperCase()));
            orderDetail.setComment(orderMap.get(ORDER_DETAIL_COMMENT));
            orderDetail.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            orderDetailDao.add(orderDetail);
            if (orderDetail.getUser().getId() != order.getUser().getId()) {
                orderDetail.setOrderDetailStatus(OrderDetailStatus.ACCEPTED);
                orderDetail.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                orderDetailDao.add(orderDetail);
            }
            transaction.commit();
            return true;
        } catch (DaoException exception) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while doing transaction rollback for creating order: " + daoException);
            }
            LOGGER.error("Error has occurred while creating order: " + exception);
            throw new ServiceException("Error has occurred while creating order: ", exception);
        } finally {
            try {
                transaction.end();
            } catch (DaoException exception) {
                LOGGER.error("Error has occurred while ending transaction for creating order: " + exception);
            }
        }
    }

    @Override
    public List<Order> issuedBooks(int page) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        OrderDao orderDao = daoProvider.getOrderDao(false);
        int startElementNumber = page * 15 - 15;
        try {
            return orderDao.issuedBooks(startElementNumber);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding issued books: " + exception);
            throw new ServiceException("Error has occurred while finding issued books: ", exception);
        } finally {
            orderDao.closeConnection();
        }
    }

    @Override
    public List<Order> searchOrderHistory(String from, String to, String search_text) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        OrderDao orderDao = daoProvider.getOrderDao(false);
        try {
            if ((from == null || to == null) && search_text == null) {
                return orderDao.orderHistory();
            } else if (from == null || to == null) {
                return orderDao.searchHistoryByQuery(search_text);
            } else if (search_text == null) {
                return orderDao.searchHistoryByDate(from, to);
            } else {
                return orderDao.searchHistoryByDateAndQuery(from, to, search_text);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while searching order history: " + exception);
            throw new ServiceException("Error has occurred while searching order history: ", exception);
        } finally {
            orderDao.closeConnection();
        }
    }

    @Override
    public List<Order> searchOrderHistoryForStudent(int id, String from, String to, String search_text) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        OrderDao orderDao = daoProvider.getOrderDao(false);
        try {
            if ((from == null || to == null) && search_text == null) {
                return orderDao.orderHistory();
            } else if (from == null || to == null) {
                return orderDao.searchHistoryByQuery(id, search_text);
            } else if (search_text == null) {
                return orderDao.searchHistoryByDate(id, from, to);
            } else {
                return orderDao.searchHistoryByDateAndQuery(id, from, to, search_text);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while searching order history for a student " + exception);
            throw new ServiceException("Error has occurred while searching order history for a student ", exception);
        } finally {
            orderDao.closeConnection();
        }
    }

    @Override
    public List<Order> requestedBooks(int page) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        OrderDao orderDao = daoProvider.getOrderDao(false);
        int startElementNumber = page * 15 - 15;
        try {
            return orderDao.requestedBooks(startElementNumber);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding requested books: " + exception);
            throw new ServiceException("Error has occurred while finding requested books: ", exception);
        } finally {
            orderDao.closeConnection();
        }
    }

    @Override
    public List<Order> searchIssuedBooksByOrderTypeAndQuery(OrderType orderType, String search, int page) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        OrderDao orderDao = daoProvider.getOrderDao(false);
        int startElementNumber = page * 15 - 15;
        List<Order> list;
        try {
            if (orderType == null && search == null) {
                list = orderDao.issuedBooks(startElementNumber);
            } else if (orderType == null) {
                list = orderDao.searchIssuedBooksByQuery(search, startElementNumber);
            } else if (search == null) {
                list = orderDao.searchIssuedBooksByOrderType(orderType, startElementNumber);
            } else {
                list = orderDao.searchIssuedBooksByOrderTypeAndQuery(orderType, search, startElementNumber);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding issued books by Order Type and Search Query: " + exception);
            throw new ServiceException("Error has occurred while finding issued books by Order Type and Search Query: ", exception);
        } finally {
            orderDao.closeConnection();
        }
        return list;
    }

    @Override
    public List<Order> searchRequestedBooksByQuery(String search, int page) throws ServiceException {
        {
            DaoProvider daoProvider = DaoProvider.getInstance();
            OrderDao orderDao = daoProvider.getOrderDao(false);
            int startElementNumber = page * 15 - 15;
            List<Order> list;
            try {
                if (search == null) {
                    list = orderDao.requestedBooks(startElementNumber);
                } else {
                    list = orderDao.searchRequestedBooksByQuery(search, startElementNumber);
                }
            } catch (DaoException exception) {
                LOGGER.error("Error has occurred while finding requested books by Search Query: " + exception);
                throw new ServiceException("Error has occurred while finding requested books by Search Query: ", exception);
            } finally {
                orderDao.closeConnection();
            }
            return list;
        }
    }

    @Override
    public Optional<Order> findOrderById(int id) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        OrderDao orderDao = daoProvider.getOrderDao(false);
        ImageDao imageDao = daoProvider.getImageDao(false);
        OrderDetailDao orderDetailDao = daoProvider.getOrderDetailDao(false);
        Optional<Order> order;
        try {
            order = orderDao.findById(id);
            order.get().setOrderDetails(orderDetailDao.findOrderDetailsByOrderId(id));
            order.get().getBook().setImages(imageDao.getImagesByBook(order.get().getBook().getId()));
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding order with order details: " + exception);
            throw new ServiceException("Error has occurred while finding order with order details: ", exception);
        } finally {
            orderDao.closeConnection();
            orderDetailDao.closeConnection();
        }
        return order;
    }
}
