package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.OrderDetail;
import com.epam.finaltask.library.exception.DaoException;

import java.util.List;

public abstract class OrderDetailDao extends BaseDao<Integer, OrderDetail> {
    public abstract List<OrderDetail> findOrderDetailsByOrderId(int id) throws DaoException;
}
