package com.epam.finaltask.library.entity;

import java.sql.Timestamp;

public class Book extends AbstractEntity {
    private String title;
    private int genreId;
    private String author;
    private String description;
    private int bookCopies;

    public Book() {
    }

    public Book(int id, Timestamp createdAt, String title, int genreId, String author, String description, int bookCopies) {
        super(id, createdAt);
        this.title = title;
        this.genreId = genreId;
        this.author = author;
        this.description = description;
        this.bookCopies = bookCopies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(int bookCopies) {
        this.bookCopies = bookCopies;
    }
}
