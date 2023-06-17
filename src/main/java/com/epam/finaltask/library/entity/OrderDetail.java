package com.epam.finaltask.library.entity;

import java.sql.Timestamp;

public class OrderDetail extends AbstractEntity{
    private int orderId;
    private int userId;
    private String status;
    private String comment;

    public OrderDetail() {
    }

    public OrderDetail(int id, Timestamp createdAt, int orderId, int userId, String status, String comment) {
        super(id, createdAt);
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.comment = comment;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
