package com.epam.finaltask.library.entity;

public class BookAvailableCopies extends AbstractEntity {
   private int bookId;
   private int availableCopies;

    public BookAvailableCopies() {
    }

    public BookAvailableCopies(int id, int bookId, int availableCopies) {
        super(id);
        this.bookId = bookId;
        this.availableCopies = availableCopies;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
