package com.epam.finaltask.library.model.service;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.exception.ServiceException;

import java.util.List;

public interface GenreService {

    boolean addGenre(String name) throws ServiceException;

    boolean editGenre(Genre genre) throws ServiceException;

    boolean isNameOccupied(String name) throws ServiceException;

    List<Genre> getAllGenres() throws ServiceException;
}
