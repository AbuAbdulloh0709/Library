package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class BookDao extends BaseDao<Integer, Book> {
    public abstract Optional<Book> findBookByTitle(String title) throws DaoException;

    abstract public List<Book> getBooksByGenre(int genre_id, int startElementNumber) throws DaoException;

    abstract public List<Book> getBooks(int startElementNumber) throws DaoException;
}
