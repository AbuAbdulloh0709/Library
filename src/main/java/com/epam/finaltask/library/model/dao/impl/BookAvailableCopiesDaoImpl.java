package com.epam.finaltask.library.model.dao.impl;

import com.epam.finaltask.library.entity.BookAvailableCopies;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.dao.BookAvailableCopiesDao;

import java.util.List;
import java.util.Optional;

public class BookAvailableCopiesDaoImpl extends BookAvailableCopiesDao {
    @Override
    public int add(BookAvailableCopies bookAvailableCopies) throws DaoException {
        return 0;
    }

    @Override
    public boolean update(BookAvailableCopies bookAvailableCopies) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws DaoException {
        return false;
    }

    @Override
    public List<BookAvailableCopies> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<BookAvailableCopies> findById(Integer integer) throws DaoException {
        return Optional.empty();
    }
}
