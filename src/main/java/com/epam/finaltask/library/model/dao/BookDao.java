package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.exception.DaoException;

import java.util.Optional;

public abstract class BookDao extends BaseDao<Integer, Book> {
    public abstract Optional<Book> findBookByTitle(String title) throws DaoException;
}
