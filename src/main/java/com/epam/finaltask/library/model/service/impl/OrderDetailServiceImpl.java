package com.epam.finaltask.library.model.service.impl;

import com.epam.finaltask.library.controller.command.RequestParameter;
import com.epam.finaltask.library.entity.OrderDetail;
import com.epam.finaltask.library.entity.enums.OrderDetailStatus;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.dao.DaoProvider;
import com.epam.finaltask.library.model.dao.OrderDao;
import com.epam.finaltask.library.model.dao.OrderDetailDao;
import com.epam.finaltask.library.model.dao.UserDao;
import com.epam.finaltask.library.model.service.OrderDetailService;
import com.epam.finaltask.library.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.epam.finaltask.library.controller.command.RequestParameter.ORDER_DETAIL_USER_ID;
import static com.epam.finaltask.library.controller.command.RequestParameter.ORDER_ID;

public class OrderDetailServiceImpl implements OrderDetailService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final OrderDetailService instance = new OrderDetailServiceImpl();

    public OrderDetailServiceImpl() {
    }

    public static OrderDetailService getInstance() {
        return instance;
    }

    @Override
    public boolean returnIssuedBook(Map<String, String> orderDetailMap) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        OrderDetailDao orderDetailDao = daoProvider.getOrderDetailDao(false);
        UserDao userDao = daoProvider.getUserDao(false);
        OrderDao orderDao = daoProvider.getOrderDao(false);

        try {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderDetailStatus(OrderDetailStatus.RETURNED);
            orderDetail.setOrderId(Integer.parseInt(orderDetailMap.get(ORDER_ID)));
            orderDetail.setUser(userDao.findById(Integer.valueOf(orderDetailMap.get(ORDER_DETAIL_USER_ID))).get());
            orderDetailDao.add(orderDetail);
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while adding new order detail: " + exception);
            throw new ServiceException("Error has occurred while adding new order detail: ", exception);
        } finally {
            orderDao.closeConnection();
            userDao.closeConnection();
            orderDetailDao.closeConnection();
        }

    }

    @Override
    public boolean acceptRequestedBook(Map<String, String> orderDetailMap) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        OrderDetailDao orderDetailDao = daoProvider.getOrderDetailDao(false);
        UserDao userDao = daoProvider.getUserDao(false);
        OrderDao orderDao = daoProvider.getOrderDao(false);

        try {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderDetailStatus(OrderDetailStatus.ACCEPTED);
            orderDetail.setOrderId(Integer.parseInt(orderDetailMap.get(ORDER_ID)));
            orderDetail.setUser(userDao.findById(Integer.valueOf(orderDetailMap.get(ORDER_DETAIL_USER_ID))).get());
            orderDetailDao.add(orderDetail);
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while adding new order detail: " + exception);
            throw new ServiceException("Error has occurred while adding new order detail: ", exception);
        } finally {
            orderDao.closeConnection();
            userDao.closeConnection();
            orderDetailDao.closeConnection();
        }
    }

    @Override
    public boolean rejectRequest(Map<String, String> orderDetailMap) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        OrderDetailDao orderDetailDao = daoProvider.getOrderDetailDao(false);
        UserDao userDao = daoProvider.getUserDao(false);
        OrderDao orderDao = daoProvider.getOrderDao(false);

        try {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderDetailStatus(OrderDetailStatus.CANCELED);
            orderDetail.setOrderId(Integer.parseInt(orderDetailMap.get(ORDER_ID)));
            orderDetail.setUser(userDao.findById(Integer.valueOf(orderDetailMap.get(ORDER_DETAIL_USER_ID))).get());
            orderDetailDao.add(orderDetail);
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while adding new order detail: " + exception);
            throw new ServiceException("Error has occurred while adding new order detail: ", exception);
        } finally {
            orderDao.closeConnection();
            userDao.closeConnection();
            orderDetailDao.closeConnection();
        }
    }
}
