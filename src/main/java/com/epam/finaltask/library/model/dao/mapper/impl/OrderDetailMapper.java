package com.epam.finaltask.library.model.dao.mapper.impl;

import com.epam.finaltask.library.entity.OrderDetail;
import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.OrderDetailStatus;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.entity.enums.UserStatus;
import com.epam.finaltask.library.model.dao.mapper.BaseMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.finaltask.library.model.dao.ColumnName.*;

public class OrderDetailMapper implements BaseMapper<OrderDetail> {
    @Override
    public List<OrderDetail> retrieve(ResultSet resultSet) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        while (resultSet.next()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(resultSet.getInt(ORDER_DETAILS_ID));
            orderDetail.setOrderId(resultSet.getInt(ORDER_ID));

            User user = new User();
            user.setId(resultSet.getInt(ORDER_DETAILS_USER_ID));
            user.setFirstName(resultSet.getString(USER_FIRST_NAME));
            user.setLastName(resultSet.getString(USER_LAST_NAME));
            user.setPassportNumber(resultSet.getString(USER_PASSPORT_NUMBER));
            user.setEmail(resultSet.getString(USER_EMAIL));
            user.setLogin(resultSet.getString(USER_LOGIN));
            user.setPassword(resultSet.getString(USER_PASSWORD));
            user.setRole(UserRole.valueOf(resultSet.getString(USER_ROLE).toUpperCase()));
            user.setAddress(resultSet.getString(USER_ADDRESS));
            user.setBirthDate(resultSet.getString(USER_BIRTH_DATE));
            user.setStatus(UserStatus.valueOf(resultSet.getString(ORDER_DETAILS_USER_STATUS).toUpperCase()));
            user.setPhoneNumber(new BigInteger(resultSet.getString(USER_PHONE_NUMBER)));
            user.setCreatedAt(resultSet.getTimestamp(USER_CREATED_AT));

            orderDetail.setUser(user);
            orderDetail.setOrderDetailStatus(OrderDetailStatus.valueOf(resultSet.getString(ORDER_DETAILS_STATUS).toUpperCase()));
            orderDetail.setComment(resultSet.getString(ORDER_DETAILS_COMMENT));
            orderDetail.setCreatedAt(resultSet.getTimestamp(ORDER_DETAILS_CREATED_AT));

            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }
}
