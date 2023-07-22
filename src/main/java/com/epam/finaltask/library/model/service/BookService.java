package com.epam.finaltask.library.model.service;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {
    boolean addBook(Book book, String fileName) throws ServiceException;

    boolean updateBook(Map<String, String> bookMap) throws ServiceException;

    boolean replaceBookImage(int book_id, String newImageUrl) throws ServiceException;

    boolean isBookTitleOccupied(String title) throws ServiceException;

    boolean isBookTitleOccupied(String title, int book_id) throws ServiceException;

    List<Book> getAllBooksByGenre(int genre_id, int page) throws ServiceException;

    List<Book> searchBooksByGenreAndSearchQuery(int genre_id, String search_text, int page) throws ServiceException;

    List<Book> getAllBooks(int page) throws ServiceException;

    Optional<Book> findById(int id) throws ServiceException;

}
