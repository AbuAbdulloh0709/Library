package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.exception.DaoException;

import java.util.Optional;

public abstract class GenreDao extends BaseDao<Integer, Genre> {
    public abstract Optional<Genre> findGenreByName(String name) throws DaoException;
}
