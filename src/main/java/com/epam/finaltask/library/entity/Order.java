package com.epam.finaltask.library.entity;

import com.epam.finaltask.library.entity.enums.OrderDetailStatus;
import com.epam.finaltask.library.entity.enums.OrderType;

import java.sql.Timestamp;
import java.util.List;

public class Order extends AbstractEntity {
    private User user;
    private Book book;
    private String issueDate;
    private String returnDate;
    private int usageDays;
    private OrderDetailStatus lastStatus;
    private OrderType orderType;
    private List<OrderDetail> orderDetails;

    public Order() {
    }

    public Order(int id, Timestamp createdAt, User user, Book book, String issueDate, String returnDate, int usageDays, OrderType orderType) {
        super(id, createdAt);
        this.user = user;
        this.book = book;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.usageDays = usageDays;
        this.orderType = orderType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getUsageDays() {
        return usageDays;
    }

    public void setUsageDays(int usageDays) {
        this.usageDays = usageDays;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderDetailStatus getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(OrderDetailStatus lastStatus) {
        this.lastStatus = lastStatus;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
}
