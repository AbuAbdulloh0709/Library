package com.epam.finaltask.library.model.service;

import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.exception.ServiceException;

import java.util.Map;

public interface OrderService {
    boolean createOrder(Map<String, String> orderMap) throws ServiceException;
}
