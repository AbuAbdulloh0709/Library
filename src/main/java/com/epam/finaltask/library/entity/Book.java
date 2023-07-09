package com.epam.finaltask.library.entity;

import java.sql.Timestamp;
import java.util.List;

public class Book extends AbstractEntity {
    private String title;
    private Genre genre;
    private String author;
    private String description;
    private int bookCopies;
    private List<Image> images;

    public Book() {
    }

    public Book(int id, Timestamp createdAt, String title, String author, String description, int bookCopies) {
        super(id, createdAt);
        this.title = title;
        this.author = author;
        this.description = description;
        this.bookCopies = bookCopies;
    }

    public Book(int id, String title, Genre genre, String author, String description, int bookCopies) {
        super(id);
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.description = description;
        this.bookCopies = bookCopies;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
