package com.epam.finaltask.library.model.service.impl;

import com.epam.finaltask.library.controller.command.RequestParameter;
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

import java.util.Map;

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
            Order newOrder = orderDao.findById(order_id).get();
            orderDetail.setOrder(newOrder);
            orderDetail.setUser(userDao.findById(Integer.valueOf(orderMap.get(ORDER_DETAIL_USER_ID))).get());
            orderDetail.setOrderDetailStatus(OrderDetailStatus.valueOf(orderMap.get(ORDER_DETAIL_STATUS).toUpperCase()));
            orderDetail.setComment(orderMap.get(ORDER_DETAIL_COMMENT));
            orderDetailDao.add(orderDetail);
            if (orderDetail.getUser().getId() != order.getUser().getId()) {
                orderDetail.setOrderDetailStatus(OrderDetailStatus.ACCEPTED);
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
}
