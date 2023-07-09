package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.Image;
import com.epam.finaltask.library.exception.DaoException;

import java.util.List;

public abstract class ImageDao extends BaseDao<Integer, Image> {
    abstract public List<Image> getImagesByBook(int book_id) throws DaoException;
}
