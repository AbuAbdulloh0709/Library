package com.epam.finaltask.library.entity;

import com.epam.finaltask.library.entity.enums.OrderDetailStatus;

public class OrderDetail extends AbstractEntity{
    private int orderId;
    private User user;
    private OrderDetailStatus orderDetailStatus;
    private String comment;

    public OrderDetail() {
    }

    public OrderDetail(int id, int orderId, User user, OrderDetailStatus status, String comment) {
        super(id);
        this.orderId = orderId;
        this.user = user;
        this.orderDetailStatus = status;
        this.comment = comment;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderDetailStatus getOrderDetailStatus() {
        return orderDetailStatus;
    }
    public void setOrderDetailStatus(OrderDetailStatus orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
