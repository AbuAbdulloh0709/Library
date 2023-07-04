package com.epam.finaltask.library.model.service;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.exception.ServiceException;

import java.util.List;

public interface BookService {
    boolean addBook(Book book) throws ServiceException;

    boolean isBookTitleOccupied(String title) throws ServiceException;

    List<Book> getAllBooks(int page) throws ServiceException;
}
