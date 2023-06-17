package com.epam.finaltask.library.entity;

import java.sql.Timestamp;

public class Order extends AbstractEntity {
    private int userId;
    private int bookId;
    private Timestamp issueDate;
    private Timestamp returnDate;
    private int usageDays;
    private String orderType;

    public Order() {
    }

    public Order(int id, Timestamp createdAt, int userId, int bookId, Timestamp issueDate, Timestamp returnDate, int usageDays, String orderType) {
        super(id, createdAt);
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.usageDays = usageDays;
        this.orderType = orderType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public int getUsageDays() {
        return usageDays;
    }

    public void setUsageDays(int usageDays) {
        this.usageDays = usageDays;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
