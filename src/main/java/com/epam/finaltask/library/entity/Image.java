package com.epam.finaltask.library.entity;

public class Image{
    private int id;
    private int bookId;
    private String url;

    private Image(){

    }

    public Image(int id, int bookId, String url) {
        this.id = id;
        this.bookId = bookId;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
